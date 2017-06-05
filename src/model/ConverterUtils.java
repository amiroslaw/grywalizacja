package model;

import database.Card;
import database.Deck;

public class ConverterUtils {
    public static Card convertCardFx(CardFx selected, Deck deck) {
        Card card = new Card();
        card.setTitle(selected.getTitleString());
        card.setDescription(selected.getDescriptionString());
        card.setImage(selected.getImageString());
        card.setType(selected.getTypeInt());
        card.setDeck(deck);
        return card;
    }

    public static CardFx convertCard(Card card) {
        CardFx cardFx = new CardFx();
        cardFx.setId(card.getId());
        cardFx.setType(card.getType());
        cardFx.setTitle(card.getTitle());
        cardFx.setDescription(card.getDescription());
        cardFx.setImage(card.getImage());
        return cardFx;
    }
}
