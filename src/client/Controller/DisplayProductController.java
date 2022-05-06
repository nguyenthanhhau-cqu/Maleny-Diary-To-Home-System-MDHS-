package client.Controller;

import client.ProductClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.ProductInfor;

import java.io.*;
import java.util.LinkedList;

public class DisplayProductController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField indexField;

    @FXML
    private TextField ingredientField;

    @FXML
    private Button leftBtn;

    @FXML
    private TextField priceField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button rightBtn;

    @FXML
    private TextField unitField;


    private LinkedList<ProductInfor> products;
    private LinkedList<ProductInfor> searchResult;

    private int counter = 0;
    private int limit;


    private FileInputStream fis;
    private ObjectInputStream ois;


    ProductClient clientProcess;




    public void initialize() {
        products = new LinkedList<>();
        searchResult = new LinkedList<>();
        products = readFromFile("ProductInformation.ser");

        indexField.setText("0");
        productNameField.setText(products.get(0).getName());
        unitField.setText(products.get(0).getUnit());
        ingredientField.setText(products.get(0).getIngredient());
        quantityField.setText(String.valueOf(products.get(0).getQuantity()));
        priceField.setText(String.valueOf(products.get(0).getUnitPrice()));


        try {
//        while (true)
            clientProcess = new ProductClient ();
//            clientProcess.getResidenceNeeded();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    void leftBtnOnclicked(ActionEvent event) {
        limit = products.size();

        if(counter -1  < limit && counter -1 >= 0 ) {
            counter --;

        }else  {
            counter = 0;

        }
        indexField.setText(String.valueOf(counter));
        productNameField.setText(products.get(counter).getName());
        unitField.setText(products.get(counter).getUnit());
        ingredientField.setText(products.get(counter).getIngredient());
        quantityField.setText(String.valueOf(products.get(counter).getQuantity()));
        priceField.setText(String.valueOf(products.get(counter).getUnitPrice()));

    }

    @FXML
    void rightBtnOnclicked(ActionEvent event) {

        limit = products.size();

        if(counter+1 < limit ) {
            counter ++;


        }else  {
            counter = limit-1;

        }
        indexField.setText(String.valueOf(counter));
        productNameField.setText(products.get(counter).getName());
        unitField.setText(products.get(counter).getUnit());
        ingredientField.setText(products.get(counter).getIngredient());
        quantityField.setText(String.valueOf(products.get(counter).getQuantity()));
        priceField.setText(String.valueOf(products.get(counter).getUnitPrice()));

    }

    public LinkedList<ProductInfor> readFromFile(String fileName) {
        try {

            File file = new File(fileName);

            if(!file.exists()){
                return null;
            }

            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);

            LinkedList<ProductInfor> list = new LinkedList<>();

            //while there is still objects to read, keep reading and add them to the list
            while (fis.available() > 0) {
                list.add((ProductInfor)ois.readObject());
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

    @FXML
    void backBtnOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/customerDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
