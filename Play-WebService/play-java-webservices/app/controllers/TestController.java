package controllers;

import play.api.libs.json.*;
import play.libs.Json;
import play.mvc.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class TestController extends Controller {

    /**
     * Multiplicacion de enteros.
     * 
     */
    public Result getMultiplication(int a, int b) {
        ObjectNode result = Json.newObject();
        result.put("resultado", a * b);
        return ok(result); 
    }

    /**
     * Comprabacion de cadena.
     */
    public Result getPalindrome(String word) {
        ObjectNode result = Json.newObject();
        word = word.replaceAll("\\s+", "");
        String reverse = new StringBuffer(word).reverse().toString();
        result.put("resultado", reverse.equalsIgnoreCase(word));
        return ok(result);
    }

}
