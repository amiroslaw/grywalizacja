package model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Card implements BaseModel {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = true)
	private int type; // typ karty 1- nagroda 1, 2- nagroda 2, 4 nic
    @DatabaseField(canBeNull = true)
	private String title;
    @DatabaseField
	private String description, image;
    // nie wiem przy potrzebne
    @ForeignCollectionField
    private ForeignCollection<Deck> decks;
    
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ForeignCollection<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ForeignCollection<Deck> decks) {
        this.decks = decks;
    }

    public Card() {
		type = 4;
		title = "pusta karta";
		description = "szablon karty z konstruktora";
		image = "link";

	}

	public Card(int type, String title) {
		this();
		this.type = type;
		this.title = title;
		// opis="szablon pustej karty z konstruktora";
		// obrazek="link";
	}

	public Card(int type, String title, String description, String image) {
		this(type, title);
		this.description = description;
		this.image = image;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "typ: " + type + "; \ntutuł: " + title + "; \nopis: " + description + "; \nlink: " + image;
	}

}
