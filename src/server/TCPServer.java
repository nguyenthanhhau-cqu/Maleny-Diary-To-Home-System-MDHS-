package server;

/* Author Mary Tom
This program demonstrates the use client server communication using writing/reading 
objects from the ObjectOutPutStream/ObjectInputStream.
The server can read objects set from a client and add them to a LinkedList.
This can also be appended to a file.
The server also recevies an attribute value (firstName) from the client, searches the 
SalariedEmployee List for all the employees having the same first name. Send these
to the client. This way a user can search for a specific option and get results from the 
data kept on the server side.
Observe the use of methods within the inner class and also the predicate and streams.
To communicate the end of object communication, an object with a specific attribute value is 
sent, in this case firstName with value "finished".
*/
//import static payRollFiles.TCPServer.clientSocket;
import java.net.*;
import java.io.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TCPServer {

    static LinkedList<CustomerDetail> customerList;
    static LinkedList<ProductInfor> productList;
    static LinkedList<DeliverySchedule> deliveryList;

    static ServerSocket serverSocket;
    final static int SERVER_PORT = 8888;
    static Socket clientSocket;


    public TCPServer() throws IOException {
        customerList = new LinkedList<>();
        productList = new LinkedList<>();
        deliveryList= new LinkedList<>();

        System.setProperty("java.net.preferIPv4Stack", "true");
        serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println(serverSocket.getInetAddress());


    }

    public void createClientThread() throws IOException {
        System.out.println("Server waiting for cient request");

        while (true) {

            clientSocket = serverSocket.accept();
            System.out.println(clientSocket.getInetAddress());//.accept();
            Connection c = new Connection(clientSocket, customerList,productList,deliveryList);

        }

    }

    public static void main(String args[]) {
        try {
            TCPServer server = new TCPServer();
            server.createClientThread();

        } catch (IOException e) {
            System.out.println(e);
        }

    }


    //this is for the tcp connection of mulitple clients.
    class Connection extends Thread {

        ObjectInputStream in;
        ObjectOutputStream out;
        Socket clientSocket;



        private LinkedList<CustomerDetail> customerList;
        private LinkedList<ProductInfor> productList;
        private LinkedList<DeliverySchedule> deliveryList;

        public Connection(Socket aClientSocket, LinkedList<CustomerDetail> customers,  LinkedList<ProductInfor> products, LinkedList<DeliverySchedule> deliveries) {
            customerList = customers;
            productList = products;
            deliveryList = deliveries;

            try {
                clientSocket = aClientSocket;

                //  registry = reg;
                //the streams are not used in this program.
                in = new ObjectInputStream(clientSocket.getInputStream());
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                this.start();
                System.out.println("thread started " + in.getClass());
            } catch (IOException e) {
                System.out.println("Connection:" + e.getMessage());
            }
        }



        //This method can open the file and load the data if there is data written to a file.
        public List<CustomerDetail> searchEntry(String firstName) {
            Predicate<CustomerDetail> find = r -> (r.getFullname().equalsIgnoreCase(firstName));
//       LinkedList <SalariedEmployee> list= new LinkedList<>();
            List<CustomerDetail> list = customerList.stream()
                    .filter(find)
                    .collect(Collectors.toList());
            //System.out.println(list);
            return list;
        }
        public List<ProductInfor> searchEntryProduct(String firstName) {
            Predicate<ProductInfor> find = r -> (r.getName().equalsIgnoreCase(firstName));
//       LinkedList <SalariedEmployee> list= new LinkedList<>();
            List<ProductInfor> list = productList.stream()
                    .filter(find)
                    .collect(Collectors.toList());
            //System.out.println(list);
            return list;
        }

        public List<DeliverySchedule> searchDelivery(String firstName) {
            Predicate<DeliverySchedule> find = r -> (r.getWeekday().equalsIgnoreCase(firstName));
//       LinkedList <SalariedEmployee> list= new LinkedList<>();
            List<DeliverySchedule> list = deliveryList.stream()
                    .filter(find)
                    .collect(Collectors.toList());
            //System.out.println(list);
            return list;
        }

        @Override
        public void run() {
            CustomerDetail data;
            ProductInfor dataProduct;
            DeliverySchedule dataDelivery;
            List<CustomerDetail> list;
            List<ProductInfor> listProduct;
            List<DeliverySchedule> listDelivery;

            String finish = "";
            try { // an echo server
                String option = (String) in.readObject();
                if (option.equalsIgnoreCase("product")) {
                    while ((dataProduct = (ProductInfor) in.readObject()) != null) {
//                        customerList.add(new CustomerDetail(data));
//                        WriteFile(customerList);
                        if (dataProduct.getName().equalsIgnoreCase("end")) {
                            System.out.println("no more");
                            break;
                        }
                        productList.add(new ProductInfor(dataProduct));

                        System.out.println("Server side list");
                        System.out.println(productList);
                    }
                } else {
                    listProduct = searchEntryProduct(option);
                    int j = 0;
                    while (!listProduct.isEmpty() && j < listProduct.size()) {
                        System.out.println(listProduct.get(j++));
                    }
                    int i = 0;
                    while (!listProduct.isEmpty() && i < listProduct.size()) {

                        out.writeObject(listProduct.get(i++));
                    }
                    out.writeObject(new ProductInfor("end"));
                }

                if (option.equalsIgnoreCase("delivery")) {
                    while ((dataDelivery = (DeliverySchedule) in.readObject()) != null) {
//                        customerList.add(new CustomerDetail(data));
//                        WriteFile(customerList);
                        if (dataDelivery.getWeekday().equalsIgnoreCase("finished")) {
                            System.out.println("no more");
                            break;
                        }
                        deliveryList.add(new DeliverySchedule(dataDelivery));

                        System.out.println("Server side list");
                        System.out.println(deliveryList);
                    }
                } else {
                    listDelivery = searchDelivery(option);
                    int j = 0;
                    while (!listDelivery.isEmpty() && j < listDelivery.size()) {
                        System.out.println(listDelivery.get(j++));
                    }
                    int i = 0;
                    while (!listDelivery.isEmpty() && i < listDelivery.size()) {

                        out.writeObject(listDelivery.get(i++));
                    }
                    out.writeObject(new ProductInfor("finished"));
                }



                if (option.equalsIgnoreCase("Enter")) {
                    while ((data = (CustomerDetail) in.readObject()) != null) {
//                        customerList.add(new CustomerDetail(data));
//                        WriteFile(customerList);
                        if (data.getFullname().equalsIgnoreCase("finished")) {
                            System.out.println("no more");
                            break;
                        }
                    customerList.add(new CustomerDetail(data));
                        System.out.println("Server side list");
                        System.out.println(customerList);

                        //the employeeList can be appended to a file.
                    }
                } else {
                    list = searchEntry(option);
                    int j = 0;
                    while (!list.isEmpty() && j < list.size()) {
                        System.out.println(list.get(j++));
                    }
                    int i = 0;
                    while (!list.isEmpty() && i < list.size()) {

                        out.writeObject(list.get(i++));
                    }
                    out.writeObject(new CustomerDetail("Finished"));
                }


            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("IO:" + e.getMessage());
            }

        }

//        private void WriteFile(LinkedList<CustomerDetail> customerList) {
//            FileOutputStream fos = null;
//            ObjectOutputStream out = null;
//            try {
//                fos = new FileOutputStream(fileName);
//                out = new ObjectOutputStream(fos);
//                out.writeObject(customerList);
//                out.close();
//                System.out.println("Object Persisted");
//            }catch(IOException ex)
//            {ex.printStackTrace();}
//        }

    }
}
  

   
