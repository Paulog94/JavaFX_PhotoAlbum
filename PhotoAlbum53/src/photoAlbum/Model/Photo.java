package photoAlbum.Model;

import javafx.scene.image.Image;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Paulo1 on 3/29/2016.
 */
public class Photo implements Serializable {
    Image m;
    private ArrayList<tag> tags;

    public Photo(Image m){
        this.m = m;
        tags = new ArrayList<tag>();
    }

    public void addTag(tag t){
        tags.add(t);
    }
    public void deleteTag(tag t){
        tags.remove(t);
    }

}
