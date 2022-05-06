package client.Controller;

import client.CustomerClient;
import client.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.CustomerDetail;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class LoginInformation {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button adminBtn;

    @FXML
    private TextField emailAddressField;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerBtn;


    private String email;
    private String passWord;
    private DatabaseConnection db;
    private CustomerDetail customer;

    JFrame f;
    CustomerClient clientprocess;


    public void initialize() {

        db = new DatabaseConnection();

        try {
//        while (true)
            clientprocess = new CustomerClient();

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    @FXML
    void adminBtnOnclicked(ActionEvent event) {


    }

    @FXML
    void loginBtnOnclicked(ActionEvent event) {
        f=new JFrame();

        email = emailAddressField.getText();
        passWord = passwordField.getText();

        customer = db.getCustomerLogin(email,passWord);


        try {
            if(customer.getEmailAddress().equalsIgnoreCase(email)){ //check if customer exist

                JOptionPane.showMessageDialog(f,"Login Success!","SUCCESS",JOptionPane.INFORMATION_MESSAGE);

                Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/customerDetails.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }

        }catch (Exception  e) {
            JOptionPane.showMessageDialog(f,"Login Fail!, Please check your user name, password again!","Fail",JOptionPane.WARNING_MESSAGE);
        }


    }

    @FXML
    void registerBtnOnclicked(ActionEvent event) throws IOException  {

    }

}
