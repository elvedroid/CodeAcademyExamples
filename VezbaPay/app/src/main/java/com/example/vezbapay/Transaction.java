package com.example.vezbapay;

import java.util.Calendar;

public class Transaction {

    private Person person;
    private double price;
    private VALUE value;
    private Calendar date;



    public enum VALUE {
        EUR,
        MKD
    }

    public Transaction(Person person, double price, VALUE value, Calendar date) {
        this.person = person;
        this.price = price;
        this.value = value;
        this.date = date;

    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public VALUE getValue() {
        return value;
    }

    public void setValue(VALUE value) {
        this.value = value;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}


