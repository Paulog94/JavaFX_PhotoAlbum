package photoAlbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;


public class searchController {

	   @FXML private ScrollPane imageGalleryField; 
	   @FXML private TextField searchFor;
	   @FXML private TextField saveAs;
	   @FXML private Button searchBtn;
	   @FXML private Button cancelBtn;
	   @FXML private Button saveAsBtn;
	   @FXML private RadioButton person;
	   @FXML private RadioButton place;
	   @FXML private RadioButton date;
	   @FXML private DatePicker startDate;
	   @FXML private DatePicker endDate;
	   @FXML private TilePane resultsTilePane = new TilePane();

}
