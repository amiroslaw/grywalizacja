package model;
//import model.*; 
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;

public class DeckTest {
    
    Deck deck; 
    
    @Before
    public void createDeck(){
		 deck = new Deck(10, 3, 2, "test deck");
		 deck.cardsList.add(new Card(1,"primary award"));
		 deck.cardsList.add(new Card(2,"secendary award 1"));
		 deck.cardsList.add(new Card(2,"secendary award 2"));
		 deck.cardsList.add(new Card(3,"smallest award 1"));
		 deck.cardsList.add(new Card(3,"smallest award 2"));
		 deck.cardsList.add(new Card(3,"smallest award 3"));
//		public ArrayList<Card> cardsList = new ArrayList<Card>();
        
    }
	
    @Test
	public void createCards_MixtTest() {
        
		deck.createDeck();
        int index = deck.cardsList.size()-1;
		
		deck.cardsList.forEach(System.out::println);
		assertEquals("size of deck", 10,  deck.cardsList.size());
		assertEquals("primary award index", 1, deck.cardsList.get(index).getType());
//		public ArrayList<Card> cardsList = new ArrayList<Card>();
		
	}
    @Test
    public void changeAmountOfCards(){
//        deck.setHowManyMediumCards(1);
        deck.cardsList.remove(0);
         
        assertEquals(5, deck.cardsList.size());
    }

}
