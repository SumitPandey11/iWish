package org.iwish.models.form;

public class InvitationEmail {

    private String email;
    private String message;

    public InvitationEmail() {
        email = "email";
        message= "message";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
