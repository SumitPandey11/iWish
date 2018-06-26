package org.iwish.models.form;

import org.iwish.models.Gift;

public class GiftAndContibutionAmount{

    private Gift gift;
    private double totalAmountContributed;


    public GiftAndContibutionAmount(double totaAmountContributed, Gift gift) {
        this.gift = gift;
        this.totalAmountContributed = totaAmountContributed;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public double getTotalAmountContributed() {
        return totalAmountContributed;
    }

    public void setTotalAmountContributed(double totalAmountContributed) {
        this.totalAmountContributed = totalAmountContributed;
    }
}
