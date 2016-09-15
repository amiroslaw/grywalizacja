package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	Connection conection;
	public ArrayList<Card> cardsList = new ArrayList<Card>();
	private int howManyCards, howManySmallCards, howManyMediumCards, ID;
	private String name;
	private int isStarted = 0;

	public Deck() {
		// nazwa = "typowa talia";
		// ileKart = 40;
		// ileMalych = 6;
		// ileSrednich = 3;

	}

	public Deck(String name) {
		this();
		this.name = name;
	}

	public Deck(int howManyCards, int howManySmallCards, int howManyMediumCards, String name) {
		this(name);
		this.howManyCards = howManyCards;
		this.howManySmallCards = howManySmallCards;
		this.howManyMediumCards = howManyMediumCards;
	}

	/**
	 * tworzy domyslne karty dla 3 kategorii TODO: nieuzywana
	 */
	public void createBlankCards() {
		// na razie najlepsza tworzona na poczatku
		cardsList.add(new Card(1, "nagroda najlepsza"));
		for (int i = 0; i < howManyMediumCards; i++) {
			cardsList.add(new Card(2, "nagroda nr2"));
		}
		for (int i = 0; i < howManySmallCards; i++) {
			cardsList.add(new Card(3, "nagroda nr3"));
		}

	}

	/**
	 * dopełnia do istniejących kart z nagrodami karty puste i sortuje je
	 */
	public void createDeck() {
		// kopiowanie najlepszej nagrody do temp bo w bd jest na koncu
		Card temp = cardsList.get(0);
		System.out.println("temp 1 nagroda "+temp);
		cardsList.remove(0);
		for (int i = 0; i < howManyCards - 1 - howManySmallCards - howManyMediumCards; i++) {
			cardsList.add(new Card());
		}
		Collections.shuffle(cardsList);
		cardsList.add(temp);
//		System.out.println("ile kart czytaTalie" + cardsList.size());
	}

	public int getIsStarted() {
		return isStarted;
	}

	public void setIsStarted(int isStarted) {
		this.isStarted = isStarted;
	}

	public int getHowManyCards() {
		return howManyCards;
	}

	public void setHowManyCards(int howManyCards) {
		this.howManyCards = howManyCards;
	}

	public int getHowManySmallCards() {
		return howManySmallCards;
	}

	public void setHowManySmallCards(int howManySmallCards) {
		this.howManySmallCards = howManySmallCards;
	}

	public int getHowManyMediumCards() {
		return howManyMediumCards;
	}

	public void setHowManyMediumCards(int howManyMediumCards) {
		this.howManyMediumCards = howManyMediumCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "nazwa: " + name + "; \nile Kart: " + howManyCards + "; \nile Malych: " + howManySmallCards
				+ "; \nile Srednich: " + howManyMediumCards;
	}

	public void setID(int ID) {
		this.ID=ID;
	}
	
	public int getID(){
		return ID;
	}
}
