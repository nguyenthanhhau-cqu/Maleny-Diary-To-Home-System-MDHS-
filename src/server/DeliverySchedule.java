package server;

import java.io.Serializable;

public class DeliverySchedule implements Serializable {
    private String weekday;
    private String postcode;
    private Double cost;
    private static long serialVersionUID = 3456858L;

    public DeliverySchedule(String weekday, String postcode, Double cost) {
        this.weekday = weekday;
        this.postcode = postcode;
        this.cost = cost;
    }

    public DeliverySchedule(String weekday) {
        this.weekday = weekday;
        this.postcode = "";
        this.cost = 0.0;
    }

    public DeliverySchedule(DeliverySchedule anotherDelivery) {
        this.weekday = anotherDelivery.weekday;
        this.postcode = anotherDelivery.postcode;
        this.cost = anotherDelivery.cost;
    }


    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        DeliverySchedule.serialVersionUID = serialVersionUID;
    }

    @Override
    public String toString() {
        return String.format("\nDelivery Date: %s %s $%.2f ", this.getWeekday(), this.getPostcode(), this.getCost());
    }
}
