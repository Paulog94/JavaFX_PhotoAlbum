package photoAlbum.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import photoAlbum.Model.Album;
import photoAlbum.Model.Photo;
import photoAlbum.Model.User;


public class searchController {

	@FXML private ScrollPane imageGalleryField; 
	@FXML private TextField searchFor;
	@FXML private TextField searchBy;
	@FXML private TextField saveAs;
	@FXML private Button searchBtn;
	@FXML private Button cancelBtn;
	@FXML private Button saveAsBtn;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private TilePane resultsTilePane = new TilePane();

	Album tempAlbum = new Album("searchResults");
	ArrayList<Photo> foundPics = tempAlbum.getPhotoList();
	private String userName;
	private ArrayList<User> savedUsers;
	private int userIndex;
	//private int albumIndex;
	String inField = null;
	String lookingFor = null;


	@FXML
	private void initialize(){
		LoadUserList();
	}

	public searchController(){

	}

	public void LoadUserList(){
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("data/UserList.bin"));
			ArrayList<User> UL;
			try {
				UL = (ArrayList<User>) ois.readObject();
				savedUsers = UL;
			} catch (ClassNotFoundException e) {
				savedUsers = new ArrayList<User>();
			}
		} catch (IOException e) {
			savedUsers = new ArrayList<User>();
			return;
		}
	}
	public void Save(ArrayList<User> users){
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("data/UserList.bin"));
			oos.writeObject(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUsername(String username){
		this.userName = username;
		for(User u : savedUsers){
			if(u.getName().equals(username))
				userIndex = savedUsers.indexOf(u);
			//currentUser = u;
		}
	}

	public void Search(){
		
		if(searchBy.getText().isEmpty()){
			return;
		}else{
			inField = searchBy.getText();
		}
		if ((inField.equals("date"))||(inField.equals("Date"))){
			LocalDate sld = startDate.getValue();
			Calendar sc =  Calendar.getInstance();
			sc.set(sld.getYear(), sld.getMonthValue(), sld.getDayOfMonth());
			
			LocalDate eld = endDate.getValue();
			Calendar ec =  Calendar.getInstance();
			ec.set(eld.getYear(), eld.getMonthValue(), eld.getDayOfMonth());	
		}else{
			lookingFor = searchFor.getText();
		}
		


	}






}
