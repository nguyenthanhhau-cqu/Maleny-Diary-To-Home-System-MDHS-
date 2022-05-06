package client;

import server.CustomerDetail;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mdhsDB"; //NEW ADJUSTED
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Anhdasai123";//YOUR PASSWORD
    private Connection connection = null; // manages connection
    private PreparedStatement checkCustomerLogin = null; // select query
    private PreparedStatement queryCheckUser = null; // select query
    static ResultSet rs;
    JFrame f;


    private PreparedStatement insertNewCustomerDedails = null; //insert query


    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // create query that selects entries with a specific last name

            checkCustomerLogin = connection.prepareStatement("SELECT * FROM customerDetails WHERE EmailAddress = ? AND PassWord = ?  ");



            // create insert that adds a new entry into the database
            insertNewCustomerDedails = connection.prepareStatement(
                    "INSERT INTO customerDetails " +
                            "( FULLNAME, EMAILADDRESS, ADDRESS,PHONENUMBER ,PASSWORD) " +
                            "VALUES ( ?, ?, ?, ?, ?)");
        } // end try // end try // end try // end try // end try // end try // end try // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        } // end catch
    } // end PersonQueries constructor
    public int addCustomer(String fullName, String emailAddress, String address, String phoneNumber, String passWord) {
        int result = 0;

        // set parameters, then execute insertNewPerson
        try {
            insertNewCustomerDedails.setString(1, fullName);
            insertNewCustomerDedails.setString(2, emailAddress);
            insertNewCustomerDedails.setString(3, address);
            insertNewCustomerDedails.setString(4, phoneNumber);
            insertNewCustomerDedails.setString(5, passWord);

            // insert the new entry; returns # of rows updated
            result = insertNewCustomerDedails.executeUpdate();
        } // end try
        catch (SQLException sqlException) {
            System.out.println("This Email address already exist, Please try a different one ");
            //close();
        } // end catch

        return result;
    }

    public CustomerDetail getCustomerLogin(String email, String passWord) {
        ResultSet resultSet = null;
        CustomerDetail cus = null;

        try {
            checkCustomerLogin.setString(1, email); // specify last name
            checkCustomerLogin.setString(2, passWord); // specify last name

            // executeQuery returns ResultSet containing matching entries
            resultSet = checkCustomerLogin.executeQuery();

            cus = new CustomerDetail();
            resultSet.next();

            cus.setFullname(resultSet.getString("FullName"));
            cus.setEmailAddress(resultSet.getString("EmailAddress"));
            cus.setAddress(resultSet.getString("Address"));
            cus.setPhoneNumber(resultSet.getString("PhoneNumber"));




        } // end try
        catch (SQLException sqlException) {
            System.out.println("Error SQL");
        } // end catch
        finally {
            try {
                resultSet.close();
            } // end try
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            } // end catch
        } // end finally

        return cus;
    }
    public void close() {
        try {
            connection.close();
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
    } // end method close
} // end class PersonQueries



