/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
/**
 * FXML Controller class
 *
 * @author andre
 */  

package client.Controller;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.CustomerDetail;

import javax.swing.*;

public class CustomerDetailsController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button addBtn;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passWordField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button submitBtn;

    @FXML
    private TextArea textAreaField;

    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    private LinkedList<CustomerDetail> customers;
    private LinkedList<CustomerDetail> searchResult;
    private DatabaseConnection db;
    private String fullname;
    private String phoneNumber;
    private String emailAddress;
    private String passWord;
    private String address;
    JFrame f;
    CustomerClient clientprocess;


    public void initialize() {
        customers = new LinkedList<>();
        searchResult = new LinkedList<>();
        db = new DatabaseConnection();

        try {
//        while (true)
            clientprocess = new CustomerClient();
            //     dataFromprovider =  clientprocess.getResidenceNeeded();

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }  


    @FXML
    void addOnclickedBtn(ActionEvent event) {
        fullname = nameField.getText();
        phoneNumber = phoneField.getText();
        emailAddress = emailField.getText();
        passWord = passWordField.getText();
        address = addressField.getText();

        if (fullname.equalsIgnoreCase("finished"))
            customers.add(new CustomerDetail("finished"));
        else
            customers.add(new CustomerDetail(fullname,phoneNumber,emailAddress,passWord,address));
            db.addCustomer(fullname,emailAddress,address,phoneNumber,passWord);

        textAreaField.setEditable(false);
        textAreaField.setText( this.customers.toString());
    }

    @FXML
    void submitOnclickedBtn(ActionEvent event) {
        LinkedList<CustomerDetail> ls = new LinkedList<CustomerDetail>();

        for (CustomerDetail s:customers)
            ls.add (new CustomerDetail (s));
        writeToFile("customerInformation.ser",ls);

      clientprocess.createClientThread(this.customers);

    }

    @FXML
    void adminBtnOnclickedBtn(ActionEvent event) throws IOException {

        f=new JFrame();
        String name= JOptionPane.showInputDialog(f,"Enter pass (pass is admin)");

        if(name.equalsIgnoreCase("admin")) {
            Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/productList.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            JOptionPane.showMessageDialog(f,"The pass is incorrect.","Alert",JOptionPane.WARNING_MESSAGE);
        }

    }

    public int writeToFile(String fileName, LinkedList<CustomerDetail> customerDetails) {
        try {

            File file = new File(fileName);
            boolean isFileExisting = file.exists();
            int employeeCount =0;
            this.fos = new FileOutputStream(fileName, true);
            /**
             * if creating new file use ObjectOutputStream which will write header
             */
            if (!isFileExisting)
                this.oos = new ObjectOutputStream(fos);
            /**
             * if the file exists and opened for appending use AppendingObjectOutputStream
             * to avoid addition of stream header again.
             */
            else
                this.oos = new AppendingObjectOutputStream (fos);
//            if (isFileExisting )
//                oos.reset();
            for (CustomerDetail emp:customerDetails)  {
                oos.writeObject(emp);
                employeeCount++;
            }
            oos.flush();
            oos.close();

            return employeeCount;
        } catch (FileNotFoundException ffe) {
            System.out.println("FNF: " + ffe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOE: " + ioe.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                System.out.println("IOE : " + ex.getMessage());
            }
        }

        return -1;
    }

    static class AppendingObjectOutputStream extends ObjectOutputStream {

        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
            this.writeStreamHeader();
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // do not write a header, but reset:
            // this line added after another question
            // showed a problem with the original
            return;
        }

    }

    @FXML
    void displayProductOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/displayProduct.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void deliveryBtnOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/DisplaySchedule.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
