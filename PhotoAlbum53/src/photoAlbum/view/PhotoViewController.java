package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import photoAlbum.Model.Photo;
import photoAlbum.Model.tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo1 on 4/11/2016.
 */
public class PhotoViewController {
   @FXML private ImageView imgView;
    @FXML private ListView TagList;
    @FXML private Label lblCaption;
    @FXML private Label lblDate;


    public void setPhoto(Photo p){

        lblCaption.setText(p.getCaption());
        lblDate.setText(p.getDate().getTime().toString());
        Image m = new Image(p.getURL());
        imgView.setImage(m);

        ObservableList<tag> ObsUserList = FXCollections.observableList(p.getTags());

        TagList.setItems(ObsUserList);
        TagList.setCellFactory(new Callback<ListView<tag>, ListCell<tag>>() {

            @Override
            public ListCell<tag> call(ListView<tag> param) {
                ListCell<tag> cell = new ListCell<tag>() {
                    @Override
                    protected void updateItem(tag t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getType()+": "+t.getValue());
                        }
                    }
                };
                return cell;
            }
        });
    }

}
