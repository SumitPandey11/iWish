package org.iwish.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Gift {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String occasion;

    @NotNull
    private float price;

    @NotNull
    @Basic
    private Date date;

    @ManyToOne
    private User user;


    @OneToMany
    @JoinColumn(name = "gift_id")
    private List<Contribution> contributions = new ArrayList<>();


    public Gift(){

    }

    public void setId(int id) { this.id = id;    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
