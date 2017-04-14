package models;

//import java.util.*;
//import javax.persistence.*;

//import play.db.jpa.*;

//@Entity
//@Table(name="country")
public class Country { //extends Model {

    public String name;
    public String capital;
    public double latitude;
    public double longitude;

    public Country(String name, String capital, double latitude, double longitude) {
        this.name = name;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
