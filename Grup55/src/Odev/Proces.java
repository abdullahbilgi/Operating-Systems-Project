package Odev;

public class Proces implements Comparable<Proces>{
	
	private int numarasi;
	private int varisZamani;
	private int oncelik;
	private int procesZamani;
	private int Mbayt;
	private int yazicilar;
	private int tarayicilar;
	private int modemler;
	private int cdler;
	private int sira;
	
	
	public Proces(){
		
	}


	
	public int getSira() {
		return sira;
	}



	public void setSira(int sira) {
		this.sira = sira;
	}
	
	public int getNumarasi() {
		return numarasi;
	}

	public void setNumarasi(int numarasi) {
		this.numarasi = numarasi;
	}

	public int getVarisZamani() {
		return varisZamani;
	}
	public void setVarisZamani(int varisZamani) {
		this.varisZamani = varisZamani;
	}
	public int getOncelik() {
		return oncelik;
	}
	public void setOncelik(int oncelik) {
		this.oncelik = oncelik;
	}
	public int getProcesZamani() {
		return procesZamani;
	}
	public void setProcesZamani(int procesZamani) {
		this.procesZamani = procesZamani;
	}
	public int getMbayt() {
		return Mbayt;
	}
	public void setMbayt(int mbayt) {
		Mbayt = mbayt;
	}
	public int getYazicilar() {
		return yazicilar;
	}
	public void setYazicilar(int yazicilar) {
		this.yazicilar = yazicilar;
	}
	public int getTarayicilar() {
		return tarayicilar;
	}
	public void setTarayicilar(int tarayicilar) {
		this.tarayicilar = tarayicilar;
	}
	public int getModemler() {
		return modemler;
	}
	public void setModemler(int modemler) {
		this.modemler = modemler;
	}
	public int getCdler() {
		return cdler;
	}
	public void setCdler(int cdler) {
		this.cdler = cdler;
	}
	
	
	@Override
	public String toString() {
		return "Proces [Numarasi=" + numarasi + ", varisZamani="  + varisZamani + ", oncelik=" + oncelik + ", procesZamani=" + procesZamani
				+ ", Mbayt=" + Mbayt + ", yazicilar=" + yazicilar + ", tarayicilar=" + tarayicilar + ", modemler="
				+ modemler + ", cdler=" + cdler +", sira=" +sira + "]";
	}



	@Override
	public int compareTo(Proces o) {  // Proseslere oncelik atandi.
		
		
		if (this.sira > o.sira) {

            return -1;

        }
        else if (this.sira < o.sira) {
            return 1;
        }
        else {
            return 0;
        }
		
		
	}
	
	
	
	
	

}
