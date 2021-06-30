package generalHandlers;

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

}
