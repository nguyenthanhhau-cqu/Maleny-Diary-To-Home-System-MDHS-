package client.Controller;

import client.ProductClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.ProductInfor;

import java.io.*;
import java.util.LinkedList;

public class productListController {

    @FXML
    private Button addProductBtn;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField ingredientField;

    @FXML
    private TextField nameField;

    @FXML
    private Button submitProductBtn;

    @FXML
    private TextArea textAreaProductField;

    @FXML
    private TextField unitField;

    @FXML
    private TextField unitPriceField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private LinkedList<ProductInfor> products;
    private LinkedList<ProductInfor> searchResult;
    private String name;
    private String unit;
    private double price;
    private String ingredient;
    private int quantity;


    ProductClient clientProcess;


    public void initialize() {
        products = new LinkedList<>();
        searchResult = new LinkedList<>();

        try {
//        while (true)
            clientProcess = new ProductClient ();
//            clientProcess.getResidenceNeeded();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    void addProductBtnOnClicked(ActionEvent event) {
        name = nameField.getText();
        unit = unitField.getText();
        price = Double.parseDouble(unitPriceField.getText());
        quantity = Integer.parseInt(quantityField.getText());

        ingredient = ingredientField.getText();

        if (name.equalsIgnoreCase("end"))
            products.add(new ProductInfor("end"));
        else
            products.add(new ProductInfor(name,quantity,unit,price,ingredient));

        textAreaProductField.setEditable(false);
        textAreaProductField.setText(this.products.toString());
    }

    @FXML
    void submitProductBtnOnclick(ActionEvent event) throws IOException {

        LinkedList<ProductInfor> ls = new LinkedList<ProductInfor>();

        for (ProductInfor s:products)
            ls.add (new ProductInfor (s));
        writeToFile("ProductInformation.ser",ls);
        clientProcess.createClientThread(products);
    }
    public int writeToFile(String fileName, LinkedList<ProductInfor> productInfors) {
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
                this.oos = new CustomerDetailsController.AppendingObjectOutputStream(fos);
//            if (isFileExisting )
//                oos.reset();
            for (ProductInfor emp:productInfors)  {
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

    class AppendingObjectOutputStream extends ObjectOutputStream {

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
    void deliveryBtnOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/deliverySchedule.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backBtnOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/customerDetails.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void viewCustomerOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/CustomerView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
