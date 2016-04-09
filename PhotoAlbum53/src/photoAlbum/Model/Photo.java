package photoAlbum.Model;

import javafx.scene.image.Image;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Paulo1 on 3/29/2016.
 */
public class Photo implements Serializable {
    private Image m;
    private String caption;
    private ArrayList<tag> tags;

    public Photo(Image m, String caption){
        this.m = m;
        this.caption = caption;
        tags = new ArrayList<tag>();
    }

    public void addTag(tag t){
        tags.add(t);
    }
    public void deleteTag(tag t){
        tags.remove(t);
    }
    public void editCaption(String caption){this.caption = caption;}
    public String getCaption(){return caption;}
    public Image getImage(){return m;}

    public boolean hasTag(tag s){

        for(tag t: tags){
            if(t.equals(s)){
                return true;
            }
        }
        return false;
    }


}
