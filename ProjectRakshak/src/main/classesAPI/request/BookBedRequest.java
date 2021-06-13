package request;

import constants.RequestCode;
import data.Bed;
import data.User;

import java.io.Serializable;

public class BookBedRequest extends Request implements Serializable {
    private Bed bed;

    public BookBedRequest(Bed bed) {
        this.bed = bed;
        requestCode = RequestCode.BOOK_BED_REQUEST;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }
}
