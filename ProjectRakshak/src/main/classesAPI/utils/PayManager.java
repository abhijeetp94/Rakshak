package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PayManager implements Serializable {
    Double gradePay;
    Double baseSalary;
    Integer paidLeave;
    List<Bonus> bonus;

    public PayManager(Double gradePay, Double baseSalary, Integer paidLeave, List<Bonus> bonus) {
        this.gradePay = gradePay;
        this.baseSalary = baseSalary;
        this.paidLeave = paidLeave;
        this.bonus = bonus;
    }

    public PayManager(Double gradePay, Double baseSalary, Integer paidLeave) {
        this.gradePay = gradePay;
        this.baseSalary = baseSalary;
        this.paidLeave = paidLeave;
        this.bonus = new ArrayList<>();
    }

    public Double getGradePay() {
        return gradePay;
    }

    public void setGradePay(Double gradePay) {
        this.gradePay = gradePay;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Integer getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(Integer paidLeave) {
        this.paidLeave = paidLeave;
    }

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }

    
}
