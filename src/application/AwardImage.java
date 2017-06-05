package application;

import java.io.File;

import javafx.scene.image.Image;

public class AwardImage {
     public final static Image AWARD1 = new Image("img/award1.png");
     public final static Image AWARD2 = new Image("img/award2.png");
     public final static Image AWARD3 = new Image("img/award3.png");
     public final static Image AWARD4 = new Image("img/cow.png");
    
    Image award;
    int index;
    final String imageSource;
    public AwardImage(Image award, int index, final String imageSource) {
        this.award = award;
        this.index = index;
        this.imageSource = imageSource;
    }
    
    public Image getSource(boolean isDefault) {
        
        if (isDefault) {
            return award;
        }
        if(isURL(imageSource)) {
            return new Image(imageSource);
        }
        
            File imageFile  = new File(imageSource);
            return new Image(imageFile.toURI().toString());
        
        
    }
    private boolean isURL(String source) {
        return source.matches("http.*");
    }

}
