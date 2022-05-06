package client.Controller;

import client.DeliverClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.DeliverySchedule;
import server.ProductInfor;

import java.io.*;
import java.util.LinkedList;

public class DisplayScheduleController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button addScheduleBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField costField;

    @FXML
    private TextField postField;

    @FXML
    private Button submitBtn;

    @FXML
    private TextField weekdayField;


    private LinkedList<DeliverySchedule> deliverySchedules;
    private LinkedList<ProductInfor> searchResult;

    private int counter = 0;
    private int limit;


    private FileInputStream fis;
    private ObjectInputStream ois;


    DeliverClient clientProcess;




    public void initialize() {
        deliverySchedules = new LinkedList<>();
        searchResult = new LinkedList<>();

        deliverySchedules = readFromFile("deliveryInformation.ser");

        weekdayField.setText(deliverySchedules.get(0).getWeekday());
        postField.setText(deliverySchedules.get(0).getPostcode());
        costField.setText(String.valueOf(deliverySchedules.get(0).getCost()));


        try {
//        while (true)
            clientProcess = new DeliverClient ();
//            clientProcess.getResidenceNeeded();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }
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
    void leftBtnOnclicked(ActionEvent event) {
        limit = deliverySchedules.size();

        if(counter -1  < limit && counter -1 >= 0 ) {
            counter --;

        }else  {
            counter = 0;

        }
        weekdayField.setText(deliverySchedules.get(counter).getWeekday());
        postField.setText(deliverySchedules.get(counter).getPostcode());
        costField.setText(String.valueOf(deliverySchedules.get(counter).getCost()));


    }

    @FXML
    void rightBtnOnclicked(ActionEvent event) {
        limit = deliverySchedules.size();

        if(counter+1 < limit ) {
            counter ++;


        }else  {
            counter = limit-1;

        }
        weekdayField.setText(deliverySchedules.get(counter).getWeekday());
        postField.setText(deliverySchedules.get(counter).getPostcode());
        costField.setText(String.valueOf(deliverySchedules.get(counter).getCost()));
    }

    public LinkedList<DeliverySchedule> readFromFile(String fileName) {
        try {

            File file = new File(fileName);

            if(!file.exists()){
                return null;
            }

            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);

            LinkedList<DeliverySchedule> list = new LinkedList<>();

            //while there is still objects to read, keep reading and add them to the list
            while (fis.available() > 0) {
                list.add((DeliverySchedule)ois.readObject());
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
