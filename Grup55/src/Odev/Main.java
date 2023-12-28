package Odev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		
		

        // Dosya yolu
        String dosyaYolu = "giris.txt";
        
        int satirSayisi = 0;
        int sutunSayisi = -1;
        
        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) { // Toplam satır sütun sayısını bulur.
            String satir;

            // Sutun sayısını belirlemek için -1 kullanılıyor

            while ((satir = br.readLine()) != null) {
                String[] veriler = satir.split(", ");

                if (sutunSayisi == -1) {
                    sutunSayisi = veriler.length;
                }

                if (veriler.length != sutunSayisi) {
                    System.err.println("Hata: Satırların uzunlukları eşit değil!");
                    return;
                }

                satirSayisi++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Matris tanımlama
        int[][] matrix = new int[satirSayisi][sutunSayisi]; // giris.txt matrisi olusturuldu.
        
        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) { //giriş.txt verileri matrise çekilir.s
            String satir;
            int satirIndex = 0;

            while ((satir = br.readLine()) != null) {
                String[] veriler = satir.split(", ");

                for (int i = 0; i < veriler.length; i++) {
                    matrix[satirIndex][i] = Integer.parseInt(veriler[i]);
                }

                satirIndex++;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Matrisi yazdırma
       /* for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }*/
        
        Proces[] prosesler = new Proces[satirSayisi]; //proses nesneleri oluşturuldu.
        Proces[] yedek = new Proces[satirSayisi]; // yedek proses nesneleri oluşturuldu.

        
        for (int i = 0; i < satirSayisi; i++) { // nesneler oluşturulur
			prosesler[i] = new Proces();
		}
        for (int i = 0; i < satirSayisi; i++) { // nesneler oluşturulur
        	yedek[i] = new Proces();
		}
       
        for (int i = 0; i < matrix.length; i++) {  //veriler matristen nesnelere çekildi
        	prosesler[i].setNumarasi(i);
        	yedek[i].setNumarasi(i);
            for (int j = 0; j < matrix[i].length; j++) {
            	if(j==0) {
            		prosesler[i].setVarisZamani(matrix[i][j]);
            		yedek[i].setVarisZamani(matrix[i][j]);

            	}
            	else if(j==1) {
            		prosesler[i].setOncelik(matrix[i][j]);
            		yedek[i].setOncelik(matrix[i][j]);

            	}
            	else if(j==2) {
            		prosesler[i].setProcesZamani(matrix[i][j]);
            		yedek[i].setProcesZamani(matrix[i][j]);

            	}
            	else if(j==3) {
            		prosesler[i].setMbayt(matrix[i][j]);
            		yedek[i].setMbayt(matrix[i][j]);
            		if(prosesler[i].getMbayt() <=64) {
            			prosesler[i].setSira(3);
            			yedek[i].setSira(3);
            		}
            		else if(prosesler[i].getMbayt() >64 && prosesler[i].getMbayt() <=1024 ) {
            			prosesler[i].setSira(2);
            			yedek[i].setSira(2);
            		}
            		else if(prosesler[i].getMbayt() >1024 ) {
            			prosesler[i].setSira(1);
            			yedek[i].setSira(1);
            		}
            	}  
            	else if(j==4) {
            		prosesler[i].setYazicilar(matrix[i][j]);
            		yedek[i].setYazicilar(matrix[i][j]);
            	}
            	else if(j==5) {
            		prosesler[i].setTarayicilar(matrix[i][j]);
            		yedek[i].setTarayicilar(matrix[i][j]);
            	}
            	else if(j==6) {
            		prosesler[i].setModemler(matrix[i][j]);
            		yedek[i].setModemler(matrix[i][j]);
            	}
            	else if(j==7) {
            		prosesler[i].setCdler(matrix[i][j]);
            		yedek[i].setCdler(matrix[i][j]);
            	}
            }
            
        }
        
        /*
        System.out.println("--------------------------");
        
        for (int i = 0; i < satirSayisi; i++) { // nesneleri yazdırır
			System.out.println(prosesler[i]);
		}*/

        
        
        Queue<Proces> FCFS = new LinkedList<Proces>();
        
        // FCFS listesi oluşturur
        for (int i = 0; i < yedek.length; i++) {
			
				FCFS.offer(yedek[i]);
			
		}
        
        
        Queue<Proces> GBG = new LinkedList<Proces>();
        
        // GBG listesi oluşturur
        for (int i = 0; i < yedek.length; i++) {
			if(yedek[i].getSira() == 2 || yedek[i].getSira() == 1) {
				GBG.offer(yedek[i]);
			}
		}
        
        
        Queue<Proces> RR = new LinkedList<Proces>();
        
        // RR listesi oluşturur
        for (int i = 0; i < prosesler.length; i++) {
			if((prosesler[i].getSira() == 2 || prosesler[i].getSira() == 1) && prosesler[i].getProcesZamani() != 1) {
				
				
					int pTime= (prosesler[i].getProcesZamani())-1;
					
					prosesler[i].setProcesZamani(pTime);
					RR.offer(prosesler[i]);
				
				
			}
		}
        
        
        /*
        System.out.println("\nFCFS\n///////////////////////////////////\n");
        
        for (Proces proces : FCFS) { // Oncelikli prosesler FCFS listesini gormek icin
			System.out.println(proces);
		}*/
        
        
        /*
        System.out.println("\nGBG\n///////////////////////////////////\n");

        for (Proces proces : GBG) { // GBG listesini gormek icin
			System.out.println(proces);
        }*/
        
        /*
        System.out.println("\nRR\n///////////////////////////////////\n");

        for (Proces proces : RR) { // RR listesini gormek icin
			System.out.println(proces);
        }*/
        
        
        
        
        Algoritmalar alg = new Algoritmalar();
        
        Thread thread1  = new Thread(()-> {

            alg.algoritmaFCFS(FCFS);
        	
        });
        
        Thread thread2  = new Thread(()-> {

        	alg.algoritmaGBG(GBG);
        	
        });
        
		Thread thread3  = new Thread(()-> {
		
			alg.algoritmaRR(RR);
		        	
		});
		
		
        
		thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException ex) {
            ;
        }
		
		System.out.println("\n\nProsesler Bitmistir !");
    
        
	}

}
