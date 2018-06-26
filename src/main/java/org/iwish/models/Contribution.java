package org.iwish.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Contribution {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private double amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Gift gift;

    @Basic
    private Date dateOfContribution = new java.sql.Date(new java.util.Date().getTime());

    public Contribution() {

    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Date getDateOfContribution() {
        return dateOfContribution;
    }

    public void setDateOfContribution(Date dateOfContribution) {
        this.dateOfContribution = dateOfContribution;
    }
}
