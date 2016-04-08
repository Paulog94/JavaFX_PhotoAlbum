package photoAlbum.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * Created by Paulo1 on 4/7/2016.
 */
public class LoginController {
    @FXML
    private Label lblLogin;

    @FXML
    private TextField txtUname;

    @FXML
    private Button btnLogin;

    public void Login(ActionEvent actionEvent){

        btnLogin.getOnMouseClicked();
        if(txtUname.getText().equals("Admin")){
            lblLogin.setText("You are The Admin");
        }
        else{
            lblLogin.setText("Welcome "+txtUname.getText());
        }

    }

}
