package org.iwish.models.form;

import org.iwish.models.Gift;

public class GiftAndContibutionAmount{

    private Gift gift;
    private double totalAmountContributed;
    private boolean isAssociatedWithAnEvent;

    public GiftAndContibutionAmount(double totaAmountContributed, Gift gift) {
        this.gift = gift;
        this.totalAmountContributed = totaAmountContributed;
    }

    public GiftAndContibutionAmount(double totalAmountContributed,Gift gift,  boolean isAssociatedWithAnEvent) {
        this.gift = gift;
        this.totalAmountContributed = totalAmountContributed;
        this.isAssociatedWithAnEvent = isAssociatedWithAnEvent;
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

    public boolean isAssociatedWithAnEvent() {
        return isAssociatedWithAnEvent;
    }

    public void setAssociatedWithAnEvent(boolean associatedWithAnEvent) {
        isAssociatedWithAnEvent = associatedWithAnEvent;
    }

}
