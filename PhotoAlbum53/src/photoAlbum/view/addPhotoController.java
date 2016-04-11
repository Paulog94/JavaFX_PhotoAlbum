package photoAlbum.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photoAlbum.Model.Photo;

public class addPhotoController {

	@FXML private TextField path;
	@FXML private TextField caption;
	@FXML private Button save;
	@FXML private Button cancel;
	@FXML private Button files;
	
	private String captionText;
	private String pathText;
	private Stage dialogStage;
	private Photo photo;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	
	
	/**
	 * @return the captionText
	 */
	public String getCaptionText() {
		return captionText;
	}
	/**
	 * @param captionText the captionText to set
	 */
	public void setCaptionText(String captionText) {
		this.captionText = captionText;
	}
	/**
	 * @return the pathText
	 */
	public String getPathText() {
		return pathText;
	}
	/**
	 * @param pathText the pathText to set
	 */
	public void setPathText(String pathText) {
		this.pathText = pathText;
	}




}
