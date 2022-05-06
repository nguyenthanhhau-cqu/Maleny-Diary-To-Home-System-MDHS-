package client.Controller;

import client.DeliverClient;
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
import server.DeliverySchedule;

import java.io.*;
import java.util.LinkedList;

public class DeliveryScheduleController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
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
    private TextArea textAreaField;

    @FXML
    private TextField weekdayField;

    private LinkedList<DeliverySchedule> deliveries;
    private LinkedList<DeliverySchedule> searchResult;

    String weekday, postcode;
    Double cost;

    DeliverClient clientProcess;


    public void initialize() {
        deliveries = new LinkedList<>();
        searchResult = new LinkedList<>();

        try {
//        while (true)
            clientProcess = new DeliverClient ();
//            clientProcess.getResidenceNeeded();

        }catch(IOException io){
            System.out.println(io.getMessage());
        }
    }

    @FXML
    void submitBtnOnclicked(ActionEvent event) {

        LinkedList<DeliverySchedule> ls = new LinkedList<>();

        for (DeliverySchedule s:deliveries)
            ls.add (new DeliverySchedule (s));
        writeToFile("deliveryInformation.ser",ls);
        clientProcess.createClientThread(deliveries);

    }

    public void addScheduleBtnOnclicked(ActionEvent event) {
        weekday = weekdayField.getText();
        postcode = postField.getText();
        cost = Double.parseDouble(costField.getText());


        if (weekday.equalsIgnoreCase("finished"))
            deliveries.add(new DeliverySchedule("finished"));
        else
            deliveries.add(new DeliverySchedule(weekday,postcode,cost));

        textAreaField.setText(this.deliveries.toString());
    }

    public void backBtnOnclicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/productList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public int writeToFile(String fileName, LinkedList<DeliverySchedule> deliverySchedules) {
        try {

            File file = new File(fileName);
            boolean isFileExisting = file.exists();
            int deliveryCount =0;
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
            for (DeliverySchedule emp:deliverySchedules)  {
                oos.writeObject(emp);
                deliveryCount++;
            }
            oos.flush();
            oos.close();

            return deliveryCount;
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
}
