package com.example.leonim.picartaodecredito.dbo;

import java.io.Serializable;
import java.util.Date;

/*
CompareTo
Equals
copy constructor
toString
Hashcode
 */

public class Release implements Comparable<Release>, Serializable {

    protected int id;
    protected double value;
    protected String type;
    protected String description;
    protected Date date;

    public Release(int id, double value, String type, String description, Date date, int invoice) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.description = description;
        this.date = date;
    }

    public Release(Release model) throws Exception{
        if(model==null)
            throw new NullPointerException("The Release model to be copied is null.");
        this.id = model.getId();
        this.value = model.getValue();
        this.type = model.getType();
        this.description = model.getDescription();
        this.date = model.getDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    @Override
    public int compareTo(Release release) {
        if(this.value < release.getValue())
            return -1;
        if(this.value > release.getValue())
            return 1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this)
            return true;
        if(obj==null)
            return false;
        if(obj.getClass() != this.getClass())
            return false;

        Release other = (Release) obj;

        if(this.id!=other.getId())
            return false;
        if(this.value != other.getValue())
            return false;
        if(!this.type.equals(other.getType()))
            return false;
        if(!this.description.equals(other.getDescription()))
            return false;
        if(!this.date.equals(other.getDate()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId();
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Release{" +
                "date=" + date +
                ", id=" + id +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
