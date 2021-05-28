package utils;

import java.io.Serializable;
import java.util.List;

public class PayManager implements Serializable {
    Double gradePay;
    Double baseSalary;
    Integer paidLeave;
    List<Integer> bonus;

    public PayManager(Double gradePay, Double baseSalary, Integer paidLeave, List<Integer> bonus) {
        this.gradePay = gradePay;
        this.baseSalary = baseSalary;
        this.paidLeave = paidLeave;
        this.bonus = bonus;
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

    public List<Integer> getBonus() {
        return bonus;
    }

    public void setBonus(List<Integer> bonus) {
        this.bonus = bonus;
    }
}
