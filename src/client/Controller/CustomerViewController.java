package client.Controller;

import client.CustomerClient;
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
import server.DeliverySchedule;
import server.ProductInfor;

import java.io.*;
import java.util.LinkedList;

public class CustomerViewController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField addressField;

    @FXML
    private Button backBtn;

    @FXML
    private TextField emailField;

    @FXML
    private Button forwardBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField indexField;

    @FXML
    private PasswordField passWordField;

    @FXML
    private TextField phoneField;

    private LinkedList<CustomerDetail> customers;
    private LinkedList<ProductInfor> searchResult;

    private int counter = 0;
    private int limit;


    private FileInputStream fis;
    private ObjectInputStream ois;


    CustomerClient clientProcess;




    public void initialize() {
        customers = new LinkedList<>();
        searchResult = new LinkedList<>();
        customers = readFromFile("customerInformation.ser");

        indexField.setText("0");

        nameField.setText(customers.get(0).getFullname());
        emailField.setText(customers.get(0).getEmailAddress());
        addressField.setText(customers.get(0).getAddress());
        passWordField.setText(customers.get(0).getPassWord());
        phoneField.setText(customers.get(0).getPhoneNumber());


        try {
//        while (true)
            clientProcess = new CustomerClient();
//            clientProcess.getResidenceNeeded();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }



    @FXML
    void leftOnclickedBtn(ActionEvent event) {
        limit = customers.size();

        if(counter -1  < limit && counter -1 >= 0 ) {
            counter --;

        }else  {
            counter = 0;

        }

        indexField.setText(String.valueOf(counter));
        nameField.setText(customers.get(counter).getFullname());
        emailField.setText(customers.get(counter).getEmailAddress());
        addressField.setText(customers.get(counter).getAddress());
        passWordField.setText(customers.get(counter).getPassWord());
        phoneField.setText(customers.get(counter).getPhoneNumber());

    }
    @FXML
    void backOnclickedBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/productList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void forwardsOnclicked(ActionEvent event) {
        limit = customers.size();

        if(counter+1 < limit ) {
            counter ++;


        }else  {
            counter = limit-1;

        }

        indexField.setText(String.valueOf(counter));
        nameField.setText(customers.get(counter).getFullname());
        emailField.setText(customers.get(counter).getEmailAddress());
        addressField.setText(customers.get(counter).getAddress());
        passWordField.setText(customers.get(counter).getPassWord());
        phoneField.setText(customers.get(counter).getPhoneNumber());
    }

    public LinkedList<CustomerDetail> readFromFile(String fileName) {
        try {

            File file = new File(fileName);

            if(!file.exists()){
                return null;
            }

            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);

            LinkedList<CustomerDetail> list = new LinkedList<>();

            //while there is still objects to read, keep reading and add them to the list
            while (fis.available() > 0) {
                list.add((CustomerDetail)ois.readObject());
            }
            return list;

        } catch (FileNotFoundException ex) {
            System.out.println("FNF : " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOE : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("CNF : " + ex.getMessage());
        } finally {
            //close all resources
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                System.out.println("IOE : " + ex.getMessage());
            }
        }
        return null;
    }

}
