package request;

import constants.RequestCode;
import data.Vaccination;

import java.io.Serializable;

public class BookVaccinationRequest extends Request implements Serializable {
    private Vaccination vaccination;

    public BookVaccinationRequest(Vaccination vaccination) {
        this.vaccination = vaccination;
        this.requestCode = RequestCode.VACCINATION_REQUEST;
    }

    public Vaccination getVaccination() {
        return vaccination;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }
}
