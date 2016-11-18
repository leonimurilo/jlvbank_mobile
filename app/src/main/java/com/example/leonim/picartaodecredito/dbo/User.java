package com.example.leonim.picartaodecredito.dbo;

import java.util.Date;

public class User {
    protected int id;
    protected String name;
    protected String cpf;
    protected String rg;
    protected String street;
    protected String city;
    protected String phone;
    protected double income;
    protected Date registrationDate;
    protected Date dateBirth;
    protected boolean status;

    public User(int id, String name, String cpf, String rg, String street,
                       String city, String phone, double income,
                       Date registrationDate, Date dateBirth, boolean status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.street = street;
        this.city = city;
        this.phone = phone;
        this.income = income;
        this.registrationDate = registrationDate;
        this.dateBirth = dateBirth;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DBOCustomer{" + "id=" + id + ", name=" + name + ", cpf=" + cpf +
                ", rg=" + rg + ", street=" + street + ", city=" + city +
                ", phone=" + phone + ", password=" +", income=" +
                income + ", registrationDate=" + registrationDate +
                ", dateBirth=" + dateBirth + '}';
    }
}
