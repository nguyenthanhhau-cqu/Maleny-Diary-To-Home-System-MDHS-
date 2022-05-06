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
public class ProductInfor implements Serializable {
    private String name;
    private int quantity;
    private String unit;
    private double unitPrice;
    private String ingredient;
    private static long serialVersionUID = 3456858L;

    public ProductInfor(String name, int quantity, String unit, double unitPrice, String ingredient) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.ingredient = ingredient;
    }

    public ProductInfor(String name, String unit, double unitPrice, String ingredient) {
        this.name = name;
        this.quantity = 0;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.ingredient = ingredient;
    }
    
    public ProductInfor(String name) {
        this.name = name;
        this.quantity = 0;
        this.unit = "";
        this.unitPrice = 0.0;
        this.ingredient = "";
    }
    public ProductInfor(ProductInfor anotherProduct) {
       this.name = anotherProduct.name;
       this.quantity = anotherProduct.quantity;
       this.unit = anotherProduct.unit;
       this.unitPrice = anotherProduct.unitPrice;
       this.ingredient = anotherProduct.ingredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        ProductInfor.serialVersionUID = serialVersionUID;
    }

    @Override
    public String toString() {
        return String.format("\nProduct information: %s %s %s %d $%.2f", this.getName(),this.getUnit(),
        this.getIngredient(),this.getQuantity(),this.getUnitPrice());
    }

   
}
