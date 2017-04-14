package controllers;

import javax.inject.Inject;

import java.sql.*;

import play.api.libs.json.*;
import play.libs.Json;
import play.*;
import play.mvc.*;
import play.db.*;

import java.util.List;
import java.util.ArrayList;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ServicesController extends Controller {

    private Database db;

    @Inject
    public ServicesController(Database db) {
        this.db = db;
    }

    public Result convertDollars(int dollars) {
        ObjectNode result = Json.newObject();
        result.put("pesos", (dollars * 18.81) );
        return ok(result);
    }

    public Result convertTemp(int temp) {
        ObjectNode result = Json.newObject();
        double cel = temp - 32;
        cel = cel * 5 / 9;
        result.put("centigrados", cel);
        return ok(result);
    }

    public Result convertTempK(double temp) {
        ObjectNode result = Json.newObject();
        double cel = temp - 273.15;
        result.put("centigrados", cel);
        return ok(result);
    }


    public Result getCapital(String country) throws SQLException {
        Connection conn = db.getConnection();
        Statement st = conn.createStatement();
        boolean executed = st.execute("SELECT capital FROM country where name = \'" + country + "\'");
        String capital = null;

        ResultSet set = st.getResultSet();
        if (executed && set.next()) {
            capital = set.getString("capital");
        } else {
            executed = false;
        }
        conn.close();

        ObjectNode result = Json.newObject();
        result.put("found", executed);
        result.put("capital", capital);
        return ok(result);
    }

    public Result getEmbassy(String country) throws SQLException {
        Connection conn = db.getConnection();
        Statement st = conn.createStatement();
        boolean executed = st.execute("SELECT latitude, longitude FROM country where name = \'" + country + "\'");
        Double lati = null;
        Double longi = null;
            
        ResultSet set = st.getResultSet();
        if (executed && set.next()) {
            lati = set.getDouble("latitude");
            longi = set.getDouble("longitude");
        } else {
            executed = false;
        }
        conn.close();
       
        ObjectNode result = Json.newObject();
        result.put("found", executed);
        result.put("latitude", lati);
        result.put("longitude", longi);
        return ok(result);    
    }

    public Result getCountries() throws SQLException {
        Connection conn = db.getConnection();
        Statement st = conn.createStatement();
        boolean executed = st.execute("SELECT name FROM country");
        List<String> names = new ArrayList<>();

        ResultSet set = st.getResultSet();
        while (executed && set.next() ) {
            names.add(set.getString("name"));
        }
        conn.close();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(names);
        ObjectNode result = Json.newObject();
        result.putArray("countries").addAll(array);
        return ok(result);
    }

}
