package utils;

import java.io.Serializable;

public class Bonus implements Serializable {
    String title;
    Double amount;

    public Bonus(String title, Double amount) {
        this.title = title;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
