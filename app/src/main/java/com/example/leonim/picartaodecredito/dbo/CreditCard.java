package com.example.leonim.picartaodecredito.dbo;

import java.util.Date;

public class CreditCard {

    protected int id;
    protected String number;
    protected String securityNumber;
    protected Date dueDate;
    protected String brand;
    protected String owner;
    protected String classification;
    protected boolean status;

    public CreditCard(int id, String number, String securityNumber,
                   Date dueDate, String brand, String owner, String classification, boolean status) {
        this.id = id;
        this.number = number;
        this.securityNumber = securityNumber;
        this.dueDate = dueDate;
        this.brand = brand;
        this.owner = owner;
        this.classification = classification;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
