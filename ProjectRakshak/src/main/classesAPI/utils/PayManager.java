package utils;

import java.io.Serializable;
import java.util.List;

public class PayManager implements Serializable {
    Integer gradePay;
    Integer baseSalary;
    Integer paidLeave;
    List<Integer> bonus;

    public PayManager(Integer gradePay, Integer baseSalary, Integer paidLeave, List<Integer> bonus) {
        this.gradePay = gradePay;
        this.baseSalary = baseSalary;
        this.paidLeave = paidLeave;
        this.bonus = bonus;
    }

    public Integer getGradePay() {
        return gradePay;
    }

    public void setGradePay(Integer gradePay) {
        this.gradePay = gradePay;
    }

    public Integer getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Integer baseSalary) {
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
