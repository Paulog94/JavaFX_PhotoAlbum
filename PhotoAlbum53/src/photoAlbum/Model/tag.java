package photoAlbum.Model;

/**
 * Tag Object
 * Created by Paulo1 on 3/29/2016.
 */
public class tag {
    //Tag type
    private String type;
    //Tag value
    private String value;

    /**
     * Constructs a tag object
     *
     * @param type tag type (ex: Location)
     * @param value tag value (ex: New York)
     */
    public tag(String type, String value){
        if(isValidTag(type)) {
            this.type = type.toLowerCase();
            this.value = value;
        }
    }

    public String getType(){return type;}
    public String getValue(){return value;}

    public boolean isValidTag(String type){

        if(type.toLowerCase().equals("location")||type.toLowerCase().equals("person"))
        return true;

        return false;
    }
}
