package org.iwish.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue
    int id;

    @NotNull
    String title;

    String occasion;

    @Basic
    private Date date;

    private String time;

    private String location;

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Guest> guests = new ArrayList<>();


    public Event() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
        An Event can have any number of gifts.
        This "ManyToMany" relationship create an intermediate join table between "Event" and "Gift" table with
        Table Name - "event_gift"  having Columns - "event_id", "gift_id".
    */
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "event_gift",
                joinColumns = { @JoinColumn(name = "event.id") },
                inverseJoinColumns = { @JoinColumn(name = "gift.id") })
        private List<Gift> gifts = new ArrayList<>();

        public void setGifts(List<Gift> gifts) {
            this.gifts = gifts;
        }
        public List<Gift> getGifts() {
            return gifts;
        }

 }

