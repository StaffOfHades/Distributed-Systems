package controllers

import javax.inject.Inject

import play.api.Play.current
import play.api.mvc._
import play.api.db._

class TestControllerInject @Inject()(db: Database) extends Controller {

    def index = Action { 
        var outString = ""

        //val conn = db.getConnection()

        //try {
        db.withConnection { conn =>

            val stmt = conn.createStatement
            val rs = stmt.executeQuery("SELECT * FROM country")
            val rsmd = rs.getMetaData

            var size = rsmd.getColumnCount            
            
            // outString += rs.toString + "\n"
            //outString += "Size: " + size + "\n"

            //outString += "Name: " + rsmd.getColumnName(2)
            for (i <- 1 to size) {
                outString += String.format("%15s", rsmd.getColumnName(i) ) + "|"
            }
            outString += "\n"
            for (i <- 1 to 64) {
                outString += "-"
            }
            outString += "\n"

            while (rs.next) {
                var i = 0
                for (i <- 1 to size) {
                    //outString += "\t" + i
                    if (i > i) outString += ", "
                    val value = rs.getString(i)
                    outString += String.format("%15s", value) + "|"
                }
                outString += "\n"
            }

        //} finally {
        //    conn.close()
        }
        Ok(outString)
    }

    def column(name: String) = Action { 
        var outString = ""

        db.withConnection { conn =>

            val stmt = conn.createStatement
            val rs = stmt.executeQuery("SELECT " + name + " FROM country")
            val rsmd = rs.getMetaData

            var size = rsmd.getColumnCount            
            
            for (i <- 1 to size) {
                outString += String.format("%15s", rsmd.getColumnName(i) ) + "|"
            }

            outString += "\n"
            for (i <- 1 to 64) {
                outString += "-"
            }
            outString += "\n"

            while (rs.next) {
                var i = 0
                for (i <- 1 to size) {
                    if (i > i) outString += ", "
                    val value = rs.getString(i)
                    outString += String.format("%15s", value) + "|"
                }
                outString += "\n"
            }

        }
        Ok(outString)
    }
    
    def capital(name: String) = Action { 
        var outString = ""

        db.withConnection { conn =>

            val stmt = conn.createStatement
            val rs = stmt.executeQuery("SELECT capital FROM country WHERE name = \'" + name + "\'")
            val rsmd = rs.getMetaData

            var size = rsmd.getColumnCount            
            
            for (i <- 1 to size) {
                outString += String.format("%15s", rsmd.getColumnName(i) ) + "|"
            }

            outString += "\n"
            for (i <- 1 to 64) {
                outString += "-"
            }
            outString += "\n"

            while (rs.next) {
                var i = 0
                for (i <- 1 to size) {
                    if (i > i) outString += ", "
                    val value = rs.getString(i)
                    outString += String.format("%15s", value) + "|"
                }
                outString += "\n"
            }

        }
        Ok(outString)
    }

    def embassy(name: String) = Action { 
        var outString = ""

        db.withConnection { conn =>

            val stmt = conn.createStatement
            val rs = stmt.executeQuery("SELECT latitude, longitude FROM country WHERE name = \'" + name + "\'")
            val rsmd = rs.getMetaData

            var size = rsmd.getColumnCount            
            
            for (i <- 1 to size) {
                outString += String.format("%15s", rsmd.getColumnName(i) ) + "|"
            }

            outString += "\n"
            for (i <- 1 to 64) {
                outString += "-"
            }
            outString += "\n"

            while (rs.next) {
                var i = 0
                for (i <- 1 to size) {
                    if (i > i) outString += ", "
                    val value = rs.getString(i)
                    outString += String.format("%15s", value) + "|"
                }
                outString += "\n"
            }

        }
        Ok(outString)
    }

}
