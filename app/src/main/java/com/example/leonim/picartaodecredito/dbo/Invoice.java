package com.example.leonim.picartaodecredito.dbo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by leonim on 22/10/2016.
 */

public class Invoice {

    private int id;
    private double value;
    private String barCode;
    private String card;
    private Date startDate;
    private Date endDate;
    private Date dueDate;
    private boolean paid;
    private ArrayList<Release> releases;

    public Invoice(String barCode, String card, Date dueDate, Date endDate,
                   int id, boolean paid, ArrayList<Release> releases,
                   Date startDate, double value) {
        this.barCode = barCode;
        this.card = card;
        this.dueDate = dueDate;
        this.endDate = endDate;
        this.id = id;
        this.paid = paid;
        this.releases = releases;
        this.startDate = startDate;
        this.value = value;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getCard() {
        return card;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public boolean isPaid() {
        return paid;
    }

    public ArrayList<Release> getReleases() {
        return new ArrayList<Release>(this.releases);
    }

    public Date getStartDate() {
        return startDate;
    }

    public double getValue() {
        return value;
    }

    public void addRelease(Release r){
        this.releases.add(r);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "barCode='" + barCode + '\'' +
                ", id=" + id +
                ", value=" + value +
                ", card='" + card + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", dueDate=" + dueDate +
                ", paid=" + paid +
                ", releases=" + releases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice invoice = (Invoice) o;

        if (getId() != invoice.getId()) return false;
        if (Double.compare(invoice.getValue(), getValue()) != 0) return false;
        if (isPaid() != invoice.isPaid()) return false;
        if (getBarCode() != null ? !getBarCode().equals(invoice.getBarCode()) : invoice.getBarCode() != null)
            return false;
        if (getCard() != null ? !getCard().equals(invoice.getCard()) : invoice.getCard() != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(invoice.getStartDate()) : invoice.getStartDate() != null)
            return false;
        if (getEndDate() != null ? !getEndDate().equals(invoice.getEndDate()) : invoice.getEndDate() != null)
            return false;
        if (getDueDate() != null ? !getDueDate().equals(invoice.getDueDate()) : invoice.getDueDate() != null)
            return false;
        return getReleases() != null ? getReleases().equals(invoice.getReleases()) : invoice.getReleases() == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getBarCode() != null ? getBarCode().hashCode() : 0);
        result = 31 * result + (getCard() != null ? getCard().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        result = 31 * result + (isPaid() ? 1 : 0);
        result = 31 * result + (getReleases() != null ? getReleases().hashCode() : 0);
        return result;
    }
}
