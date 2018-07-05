package org.iwish.models.form;

public class UsersContributionsByGiftId {
    private double totalamount;
    private String name;
    private String email;
    private int giftId;

    public UsersContributionsByGiftId(double totalamount, int giftId , String name, String email) {
        this.totalamount = totalamount;
        this.name = name;
        this.email = email;
        this.giftId = giftId;
    }

    public UsersContributionsByGiftId() {

    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGiftIdId() {
        return giftId;
    }

    public void setGiftIdId(int id) {
        this.giftId = id;
    }
}
