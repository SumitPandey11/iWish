package org.iwish.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Guest {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String guestEmail;

    @ManyToOne
    private Event event;

    public Guest() {

    }

    public int getId() {
        return id;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
