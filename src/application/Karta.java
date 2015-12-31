package application;

public class Karta {
	private int typ; // typ karty 1- nagroda 1, 2- nagroda 2, 4 nic
	private String tytul, opis, obrazek;

	public int getTyp() {
		return typ;
	}

	public void setTyp(int typ) {
		this.typ = typ;
	}

	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getObrazek() {
		return obrazek;
	}

	public void setObrazek(String obrazek) {
		this.obrazek = obrazek;
	}

	public Karta() {
		typ = 4;
		tytul = "pusta karta";
		opis = "szablon karty z konstruktora";
		obrazek = "link";

	}

	public Karta(int typ, String tytul) {
		this();
		this.typ = typ;
		this.tytul = tytul;
		// opis="szablon pustej karty z konstruktora";
		// obrazek="link";
	}

	public Karta(int typ, String tytul, String opis, String obrazek) {
		this(typ, tytul);
		this.opis = opis;
		this.obrazek = obrazek;
	}

	public String toString() {
		return "typ: " + typ + "; \ntutuł: " + tytul + "; \nopis: " + opis + "; \nlink: " + obrazek;
	}

}
