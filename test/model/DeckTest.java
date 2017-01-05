package model;
//import model.*; 
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {
    
    Deck deck; 
    
    @Before
    public void createDeck(){
		 deck = new Deck(6, 3, 2, "test deck");
//		public ArrayList<Card> cardsList = new ArrayList<Card>();
        
    }
	
    @Test
	public void createBlankCardsTest() {
		Deck deck = new Deck(40, 6, 3, "test deck");
//		public ArrayList<Card> cardsList = new ArrayList<Card>();
		
	}

}
