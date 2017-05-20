package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CardFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty type = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty image = new SimpleStringProperty();
    public int getIdInt(){
        return id.get();
    }
    public IntegerProperty getId() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public Integer getTypeInt(){
        return type.get();
    }
    public IntegerProperty getType() {
        return type;
    }
    public void setType(int type) {
        this.type.set(type);
    }
    public StringProperty getTitle() {
        return title;
    }
    public String getTitleString() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public String getDescriptionString() {
        return description.get();
    }
    public StringProperty getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description.set(description);
    }
    public String getImageString() {
        return image.get();
    }
    public StringProperty getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image.set(image);
    }
    @Override
    public String toString() {
        return "CardFx [id=" + id + ", type=" + type + ", title=" + title + ", description=" + description + ", image="
                + image + "]";
    }
    
    

}
