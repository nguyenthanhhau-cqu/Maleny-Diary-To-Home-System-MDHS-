package client;


/* Author Mary Tom
This program demonstrates the use of java rmi and registry service.
The client looks up for a book object registered by the server.
The client can invoke the methods from  the BookInterface available to the client.
The tcp connections are only required if other messages are to be exchanged between client server 
other than the using the objects from the registry.
*/
import server.CustomerDetail;

import java.net.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerClient {

    private Socket s = null;
     private static LinkedList<CustomerDetail> customers;// = new LinkedList<>();;
     ServerConnection conn;
     ProviderConnection provider;
     Thread providerThread;
    public CustomerClient () throws IOException{

       this.customers = new LinkedList<>();
       
    }
   /**
    * Method to create a thread to connect to server and send the data entered by the user.
    * @param customers 
    */
   public void createClientThread(LinkedList<CustomerDetail> customers) {

                 conn = new ServerConnection(customers);
                 customers.clear();
  
    }
    @SuppressWarnings("empty-statement")
   public  LinkedList<CustomerDetail> getEmployees (){
       while (providerThread.isAlive())
           ;
       System.out.println("after thread" +customers);
       return customers;
   }
    public void createProviderThread(String criterion){
       providerThread = new Thread(new ProviderConnection( criterion));
        providerThread.start();
        System.out.println("slariedEmplyees "+ customers);
    }
  /**
   * This class is written as an extension to Thread class.
   * The run method gets the list of employees entered by the user using the GUI.
   * The first output going to the server with an "Enter" indicates to the
   * server that a set of data is coming.
   * The completion of data transmission is indicated by sending and Object with 
   * firstName "finished"
   * The TCP communication is synchronous. Client communication
   * needs to send required data to let the Server know client's needs.
   */
   class ServerConnection extends Thread {
   private int serverPort;	
   ObjectInputStream in;
   ObjectOutputStream out;
    private Socket s = null;
    private  boolean updated = false;
    private LinkedList<CustomerDetail> employeesLocal = new LinkedList<>();
   public ServerConnection (LinkedList<CustomerDetail>  employeesList ) {
		
   try {
       serverPort = 8888;
       
        s = new Socket("localhost", serverPort); 
       System.out.println(s.getInetAddress());
        out =new ObjectOutputStream(s.getOutputStream());
	in = new ObjectInputStream( s.getInputStream());
        System.out.println("out "+ out.getClass()+ "in "+in.getClass());
/**
 * The data received is copied to the memory space of the thread.
 */
        System.out.println(employeesList);
        for (int i =0; i<employeesList.size();i++){
           this.employeesLocal.add(new CustomerDetail((CustomerDetail)employeesList.get(i)));
        }
     /**
      * This method starts the thread and executes the run() method.
      */   
         this.start();
  
   } catch(IOException e) {
       System.out.println("Connection:"+e.getMessage());
     }
   }
   
   public LinkedList<CustomerDetail> getData(){
         return this.employeesLocal;
   }
  
   @Override
   public void run()
    {
        System.out.println("thread running");
        try{
            out.writeObject("Enter");
          while(employeesLocal.size()>0){
              CustomerDetail data = employeesLocal.removeFirst();
              System.out.println(data);
              out.writeObject(data);
          }
          //Server can use this to know that last object sent
          if (employeesLocal.size() == 0)
              out.writeObject(new CustomerDetail("finished"));
        }catch (IOException e) {
                    System.err.println("IOException:  " + e);
      }   

        }      
  }
  /**
   * This class is written implementing the Runnable interface rather than 
   * extending the Thread class. In both ways we can implement the run() method to execute
   * the required tasks.
   * This thread is used to connect to the server and send the search string 
   * entered by the user to the server.
   * The run () method send the string and get the search result from the server.
   * As this is a thread once the run() finishes it cuts off.
   */
   class ProviderConnection implements Runnable {
    private int serverPort;	
    ObjectInputStream in;
    ObjectOutputStream out;
    private Socket s = null;
    private  boolean updated = false;
   // private ResidenceNeedful residence = null;
    private String firstName;
    private LinkedList<CustomerDetail> employeesSalaried;
    public ProviderConnection ( String searchName){
	employeesSalaried = new LinkedList<> ();	
    try {
       serverPort = 8888;
       s = new Socket("localhost", serverPort); 
       System.out.println(s.getInetAddress());
       out =new ObjectOutputStream(s.getOutputStream());
       in = new ObjectInputStream( s.getInputStream());
       System.out.println("out"+ out.getClass()+ "in"+in.getClass());
       firstName = new String(searchName);
 
      } catch(IOException e) {
       System.out.println("Connection:"+e.getMessage());
     }
   }
   

   @Override
   public void run() {
       CustomerDetail data;
   
        System.out.println("thread running");
        try{
           out.writeObject(firstName);
           while( (data = (CustomerDetail)in.readObject())!= null){
             if (data.getFullname().equalsIgnoreCase("finished")){
              System.out.println("no more");
              break;
             }
             System.out.println("data from server" +data);

             customers.add(new CustomerDetail(data));
             
             System.out.println("employees matching criterion "+customers);
           }
          }catch (IOException |ClassNotFoundException e) {
        }      
   }
  }
//  public static void main (String args[]) {
//     
//    }	
	
   }

