package org.iwish.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * This is User Entity Class, This call will hold the username, email and password.
 * Password will be hasing and salted
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    @NotNull
    private String password;

    @Email
    private String email;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Gift> gifts = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserGroup> userGroups = new ArrayList<>();

    public User(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
