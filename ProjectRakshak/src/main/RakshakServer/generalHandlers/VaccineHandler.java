package generalHandlers;

import data.Vaccination;
import data.Vaccine;
import mainPack.Main;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccineHandler {


    public static List<Vaccine> getVaccines(){
        String vaccineQuery = "SELECT * FROM vaccines";
        List<Vaccine> vaccines = new ArrayList<>();
        try {
            PreparedStatement vaccineStatement = Main.connection.prepareStatement(vaccineQuery);
            ResultSet vaccineResult = vaccineStatement.executeQuery();
            while (vaccineResult.next()){
                String name = vaccineResult.getString("name");
                String type = vaccineResult.getString("type");
                String vaccine_id = vaccineResult.getString("vaccine_id");
                int quantity = vaccineResult.getInt("quantity");
                int dose = vaccineResult.getInt("dose");
                boolean available = (vaccineResult.getInt("available")!=0);
                vaccines.add(new Vaccine(name, type, quantity, available, vaccine_id, dose));
            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        return vaccines;
    }
    public static boolean bookVaccination(Vaccination vaccination){
        String vaccineQuery = "SELECT * FROM vaccines WHERE vaccine_id = ?";
        String vaccinationQuery = "INSERT INTO vaccination (user_id, vaccine, dose, date, vaccinated) " +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement vaccineStatement = Main.connection.prepareStatement(vaccineQuery);
            PreparedStatement vaccinationStatement = Main.connection.prepareStatement(vaccinationQuery);
            vaccineStatement.setString(1, vaccination.getVaccine().getVaccineID());
            ResultSet vaccineResult = vaccineStatement.executeQuery();
            if(vaccineResult.next()){
                if(vaccineResult.getInt("available")==0){
                    return false;
                }
                vaccinationStatement.setString(1, vaccination.getUserID());
                vaccinationStatement.setInt(2, vaccineResult.getInt("_id"));
                vaccinationStatement.setInt(3, vaccination.getDose());
                vaccinationStatement.setString(4, vaccination.getDate().format(Main.formatter));
                vaccinationStatement.setInt(5, vaccination.isVaccinated()?1:0);
                vaccinationStatement.execute();
            }

        } catch (SQLException se){
            se.printStackTrace();
            return false;
        }
        return true;
    }

}
