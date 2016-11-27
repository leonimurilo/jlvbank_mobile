package com.example.leonim.picartaodecredito.dbo;

import java.util.ArrayList;
import java.util.Date;

public class CreditCard implements java.io.Serializable{

    private int id;
    private String number;
    private String securityNumber;
    private String password;
    private Date dueDate;
    private String brand;
    private String owner;
    private String classification;
    private boolean status;
    private ArrayList<Invoice> invoiceArrayList;

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

    public String getPassword() {
        return password;
    }

    public ArrayList<Invoice> getInvoiceArrayList() {
        return invoiceArrayList;
    }

    public void setInvoiceArrayList(ArrayList<Invoice> invoiceArrayList) {
        this.invoiceArrayList = invoiceArrayList;
    }

    public void setPassword(String password) {
        this.password = password;
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
