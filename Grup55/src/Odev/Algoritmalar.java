package Odev;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Algoritmalar implements Algorithms {
	
	 // sistem kaynaklari
    int printersAvailable = 2;
    int scannersAvailable = 1;
    int modemsAvailable = 1;
    int cdsAvailable = 2;
	
	private Lock lock = new ReentrantLock();
	
	
	
	public void algoritmaFCFS(Queue<Proces> queue){ // FCFS Algoritmasi

        lock.lock();
        
        System.out.println("~~FCFS Algoritması Çalışıyor~~");
        
        int sure = 0;
        
        List<Proces> copyOfQueue = new ArrayList<>(queue);
        
        for (Proces proces : copyOfQueue) { // Banker Algoritması kullanılarak prosesler işlendi 
			
        	if(proces.getSira() == 3) {
        		int prt = proces.getYazicilar();
        		int tryc= proces.getTarayicilar();
        		int mdm= proces.getModemler();
        		int cd= proces.getCdler();
        		if(proces.getYazicilar() <= printersAvailable &&
        				proces.getTarayicilar() <= scannersAvailable &&
        				proces.getModemler() <= modemsAvailable &&
        				proces.getCdler() <= cdsAvailable
        				) {
        			printersAvailable-=prt; // Kaynaklar tahsis edildi
        			scannersAvailable-=mdm;
        			modemsAvailable-=tryc;
        			cdsAvailable-=cd;
        			
        			
        			for (int i = 0; i < proces.getProcesZamani(); i++) { 
        				System.out.println("P"+proces.getNumarasi()+" işleniyor " +sure+ ". saniye ");
        				sure++;
					}
        			
        			printersAvailable+=prt; // Kaynaklar geri verildi
        			scannersAvailable+=mdm;
        			modemsAvailable+=tryc;
        			cdsAvailable+=cd;
        			
        			sure+=proces.getVarisZamani();
        			
        			System.out.println("P"+proces.getNumarasi()+" tamamlandı! ");
        			queue.poll();
        			
        			 
        			
        			
        			
        		}
        		else {
        			System.out.println("P"+queue.peek().getNumarasi() + " Prosesi için yetersiz kaynak!");
        			System.out.println("P"+queue.peek().getNumarasi() + " Prosesinin sonlandırılması bekleniyor...(20 saniye bekleyiniz) ");
        			try {
        	            Thread.sleep(20000);
        	            System.out.println("P"+queue.peek().getNumarasi() + " Prosesi sonlandırıldı!");
        	            queue.poll();
        				sure+=20; // deadlock bekleme süresi
        	        } catch (InterruptedException ex) {
        	            ;
        	        }
        		}
        	}
        	else {
        		System.out.println("P"+proces.getNumarasi()+ " Alt görevlendiriciye aktarıldı!");
        		queue.poll();
        	}
        	
		}
        
        System.out.println("~~FCFS Algoritması Bitti~~");
        
        lock.unlock();
    }
	
	
	public void algoritmaGBG(Queue<Proces> queue){ // GBG Algoritmasi

        lock.lock();
        
        System.out.println("~~GBG Algoritması Çalışıyor~~");
        
        int sure = 0;
        
        List<Proces> copyOfQueue = new ArrayList<>(queue);
        
        for (Proces proces : copyOfQueue) { // Banker Algoritması kullanılarak prosesler işlendi 
			
        	
        		int prt = proces.getYazicilar();
        		int tryc= proces.getTarayicilar();
        		int mdm= proces.getModemler();
        		int cd= proces.getCdler();
        		if(proces.getYazicilar() <= printersAvailable &&
        				proces.getTarayicilar() <= scannersAvailable &&
        				proces.getModemler() <= modemsAvailable &&
        				proces.getCdler() <= cdsAvailable
        				) {
        			printersAvailable-=prt; // Kaynaklar tahsis edildi
        			scannersAvailable-=mdm;
        			modemsAvailable-=tryc;
        			cdsAvailable-=cd;
        			
        			if(proces.getProcesZamani() != 1) {
        				 			
        			
	        			for (int i = 0; i < 1; i++) { // quantalama (q=1)
	        				System.out.println("P"+proces.getNumarasi()+" işleniyor " +sure+ ". saniye ");
	        				sure++;
						}
	        			
	        			proces.setProcesZamani((proces.getVarisZamani())-1);
	        			
	        			printersAvailable+=prt; // Kaynaklar geri verildi
	        			scannersAvailable+=mdm;
	        			modemsAvailable+=tryc;
	        			cdsAvailable+=cd;
	        			
	        			System.out.println("P"+proces.getNumarasi()+ " Alt görevlendiriciye aktarıldı!");
	        			queue.poll();
	        			        			
	        			sure=proces.getVarisZamani()+1;
	        			
			
        			}
        			else {
        				System.out.println("P"+proces.getNumarasi()+" tamamlandı! ");
            			queue.poll();
        				
        			}
        			 		
        		}
        		else {
        			System.out.println("P"+queue.peek().getNumarasi() + " Prosesi için yetersiz kaynak!");
        			System.out.println("P"+queue.peek().getNumarasi() + " Prosesinin sonlandırılması bekleniyor...(20 saniye bekleyiniz) ");
        			
        			try {
        	            Thread.sleep(20000);
        	            System.out.println("P"+queue.peek().getNumarasi() + " Prosesi sonlandırıldı!");
        	            queue.poll();
    				sure+=20; // deadlock bekleme süresi
        	        } catch (InterruptedException ex) {
        	            ;
        	        }
        		}
        	
		}
        
        System.out.println("~~GBG Algoritması Bitti~~");

        
        lock.unlock();
    }
	
	
	public void algoritmaRR(Queue<Proces> queue){ // RR Algoritmasi

        lock.lock();
        
        System.out.println("~~RR Algoritması Çalışıyor~~");
        
        int sure = 0;
        int sayac=0; // quantalama kontrolü (q=1)
        
        Queue<Proces> RR = new LinkedList<Proces>();
        
        RR = queue;
        
        List<Proces> copyOfQueue = new ArrayList<>(queue);
        
        Queue<Proces> hazir = new LinkedList<Proces>();
        
        Queue<Proces> islem = new LinkedList<Proces>();
        
        
        while(RR.isEmpty() != true) {
        	
        	for (Proces proces : copyOfQueue) {
        		
        		if(proces.getVarisZamani() == sayac) {
        			hazir.offer(RR.poll());
        		}
        		
        	}
        	
        	if(sure == sayac) {
        		islem.offer(hazir.poll());
        		
        		if(islem.peek().getProcesZamani() == 1) {
        			
        			int prt = islem.peek().getYazicilar();
            		int tryc= islem.peek().getTarayicilar();
            		int mdm= islem.peek().getModemler();
            		int cd= islem.peek().getCdler();
        			
        			if(islem.peek().getYazicilar() <= printersAvailable &&
        					islem.peek().getTarayicilar() <= scannersAvailable &&
        							islem.peek().getModemler() <= modemsAvailable &&
        									islem.peek().getCdler() <= cdsAvailable
            				) {// Banker Algoritması kullanılarak prosesler işlendi 
            			printersAvailable-=prt; // Kaynaklar tahsis edildi
            			scannersAvailable-=mdm;
            			modemsAvailable-=tryc;
            			cdsAvailable-=cd;
            			
            			System.out.println("P"+islem.peek().getNumarasi()+" işleniyor " +sure+ ". saniye ");
            			System.out.println("P"+islem.peek().getNumarasi()+" tamamlandı! ");
            			
            			printersAvailable+=prt; // Kaynaklar geri verildi
	        			scannersAvailable+=mdm;
	        			modemsAvailable+=tryc;
	        			cdsAvailable+=cd;
	        			islem.poll();
	        			
	        			sure++;
            			
            			
            			
        			}
        			else {// kaynak yetersiz
        				System.out.println("P"+islem.peek().getNumarasi() + " Prosesi için yetersiz kaynak!");
            			System.out.println("P"+islem.peek().getNumarasi() + " Prosesinin sonlandırılması bekleniyor...(20 saniye bekleyiniz) ");
            			
            			try {
            	            Thread.sleep(20000);
            	            System.out.println("P"+queue.peek().getNumarasi() + " Prosesi sonlandırıldı!");
            	            islem.poll();
            	        } catch (InterruptedException ex) {
            	            ;
            	        }
            			sure++;
            			
        			}
        			
        			
        		}
        		else {// !=1
        			
        			int prt = islem.peek().getYazicilar();
            		int tryc= islem.peek().getTarayicilar();
            		int mdm= islem.peek().getModemler();
            		int cd= islem.peek().getCdler();
        			
        			if(islem.peek().getYazicilar() <= printersAvailable &&     
        					islem.peek().getTarayicilar() <= scannersAvailable &&
        							islem.peek().getModemler() <= modemsAvailable &&
        									islem.peek().getCdler() <= cdsAvailable
            				) {// Banker Algoritması kullanılarak prosesler işlendi 
            			printersAvailable-=prt; // Kaynaklar tahsis edildi
            			scannersAvailable-=mdm;
            			modemsAvailable-=tryc;
            			cdsAvailable-=cd;
            			
            			System.out.println("P"+islem.peek().getNumarasi()+" işleniyor " +sure+ ". saniye ");
            			System.out.println("P"+islem.peek().getNumarasi()+" hazır kuyruğuna alındı! ");
            			islem.peek().setProcesZamani(islem.peek().getProcesZamani()-1);
            			islem.peek().setProcesZamani(islem.peek().getVarisZamani()+1);
            			hazir.offer(islem.poll());
            			
            			printersAvailable+=prt; // Kaynaklar geri verildi
	        			scannersAvailable+=mdm;
	        			modemsAvailable+=tryc;
	        			cdsAvailable+=cd;
	        			
	        			
	        			sure++;
            			
            			
            			
        			}
        			else {// kaynak yetersiz
        				System.out.println("P"+islem.peek().getNumarasi() + " Prosesi için yetersiz kaynak!");
            			System.out.println("P"+islem.peek().getNumarasi() + " Prosesinin sonlandırılması bekleniyor...(20 saniye bekleyiniz) ");
            			
            			try {
            	            Thread.sleep(20000);
            	            System.out.println("P"+queue.peek().getNumarasi() + " Prosesi sonlandırıldı!");
            	            islem.poll();
            	        } catch (InterruptedException ex) {
            	            ;
            	        }
            			sure++;
        			}
        			
        			
        			
        		}
        		
        		
        	}
        	else;
        	
        
        	sayac++;
        	
        	
        }
        

        System.out.println("~~RR Algoritması bitti~~");

        
        lock.unlock();
    }
	
	
	
	
	

}
