/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.Serializable;

/**
 *
 * @author andre
 */
public class CustomerDetail implements Serializable {
    private String fullname;
    private String phoneNumber;
    private String emailAddress;
    private String passWord;
    private String address;
    private static long serialVersionUID = 3456858L;
    
    //constructor

    public CustomerDetail(String fullname, String phoneNumber, String emailAddress, String passWord, String address) {
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.passWord = passWord;
        this.address = address;
    }
    
    public CustomerDetail(CustomerDetail anotherCustomer) {
        this.fullname = anotherCustomer.fullname;
        this.phoneNumber = anotherCustomer.phoneNumber;
        this.emailAddress = anotherCustomer.emailAddress;
        this.passWord = anotherCustomer.passWord;
        this.address = anotherCustomer.address;
    }
    
    public CustomerDetail(String fullName) {
        this.fullname = fullName;
        this.phoneNumber="";
        this.emailAddress = "";
        this.passWord = "";
        this.address = "";
    }

    public CustomerDetail() {

    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        CustomerDetail.serialVersionUID = serialVersionUID;
    }

    @Override
    public String toString() {
        return "\nCustomer Information: " + "  " +this.getFullname() + "  " +this.getPhoneNumber() + "  " +this.getEmailAddress() + "|| The Address: " + this.getAddress() ;
    }
    
    
    
}
