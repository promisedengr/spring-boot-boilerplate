package com.nichoshop.main.util;

public class RichMoney {
    private int amount;
    private int currencyId;

    public RichMoney(int amount, int currencyId) {
        this.setAmount(amount);
        this.setCurrencyId(currencyId);
    }

    /**
     * @return int return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return int return the currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * @param currencyId the currencyId to set
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

}
