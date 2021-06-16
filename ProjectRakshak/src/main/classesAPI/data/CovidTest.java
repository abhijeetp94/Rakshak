package data;

import java.io.Serializable;
import java.time.LocalDate;

public class CovidTest implements Serializable {
    private String uid;
    private LocalDate testDate;
    private boolean isPositive;

    public CovidTest(String uid, LocalDate testDate, boolean isPositive) {
        this.uid = uid;
        this.testDate = testDate;
        this.isPositive = isPositive;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }
}
