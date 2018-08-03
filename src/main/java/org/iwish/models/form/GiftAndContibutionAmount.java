package org.iwish.models.form;

import org.iwish.models.Gift;

public class GiftAndContibutionAmount{

    private Gift gift;
    private double totalAmountContributed;
    private boolean isAssociatedWithCurrentEvent;
    private boolean isAssociatedWithAnyEvent;

    public GiftAndContibutionAmount(double totaAmountContributed, Gift gift) {
        this.gift = gift;
        this.totalAmountContributed = totaAmountContributed;
    }

    public GiftAndContibutionAmount(double totalAmountContributed,Gift gift, boolean isAssociatedWithAnEvent) {
        this.gift = gift;
        this.totalAmountContributed = totalAmountContributed;
        this.isAssociatedWithCurrentEvent = isAssociatedWithAnEvent;
    }

    public GiftAndContibutionAmount(double totalAmountContributed, Gift gift, boolean isAssociatedWithAnEvent, boolean isAssociatedWithAnyEvent) {
        this.gift = gift;
        this.totalAmountContributed = totalAmountContributed;
        this.isAssociatedWithCurrentEvent = isAssociatedWithAnEvent;
        this.isAssociatedWithAnyEvent = isAssociatedWithAnyEvent;
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

    public boolean isAssociatedWithCurrentEvent() {
        return isAssociatedWithCurrentEvent;
    }

    public void setAssociatedWithCurrentEvent(boolean associatedWithAnEvent) {
        isAssociatedWithCurrentEvent = associatedWithAnEvent;
    }

    public boolean isAssociatedWithAnyEvent() {
        return isAssociatedWithAnyEvent;
    }

    public void setAssociatedWithAnyEvent(boolean associatedWithAnyEvent) {
        isAssociatedWithAnyEvent = associatedWithAnyEvent;
    }

}
