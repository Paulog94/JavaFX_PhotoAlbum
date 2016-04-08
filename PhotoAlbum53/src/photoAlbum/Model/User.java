package photoAlbum.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Paulo1 on 3/29/2016.
 */
public class User implements Serializable {

    private String username;
    private ArrayList<Album> albums;

    public User(String name) {
        username = name;
        albums = new ArrayList<Album>();
    }

    public String getName(){
        return username;
    }
    public void setName(String name){
        username = name;
    }

    /**
     * Adds a new Album for the User
     * @param name
     */
    public void addAlbum(String name){
        if(containsAlbum(name)){
            return;
        }
        albums.add(new Album(name));
    }

    /**
     * Retrieves album with a specific name
     * @param name Name of album to retrieve
     * @return Albume with the specific name
     */
    public Album getAlbum(String name){
        for(Album a: albums){
            if(name.equals(a.getName()))
                return a;
        }
        return null;
    }

    /**
     * Checks if a user's Albums contains a specific album
     * @param name Album name
     * @return true if there is an album with the same name
     */
    public boolean containsAlbum(String name){
        for(Album a: albums){
            if (name.equals(a.getName()))
                return true;
        }
        return false;
    }
}
