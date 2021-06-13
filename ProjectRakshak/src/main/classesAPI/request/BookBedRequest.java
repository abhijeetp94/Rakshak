package request;

import constants.RequestCode;
import data.Bed;
import data.User;

import java.io.Serializable;

public class BookBedRequest extends Request implements Serializable {
    private User user;
    private Bed bed;

    public BookBedRequest(User user, Bed bed) {
        this.user = user;
        this.bed = bed;
        requestCode = RequestCode.BOOK_BED_REQUEST;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }
}
