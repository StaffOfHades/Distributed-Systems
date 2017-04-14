let server = "localhost"
let port = ":9000"

function get(api, result) {
    $.ajax( {
        type: "GET",
        url: "http://" + server + port + api,
        data: { get_param: "value" },
        dataType: "json",
        success: result   
    });
}

function populateSelect(select) {
    get("/services/getCountries", function (data) {
        var countries = data.countries;
        $.each(countries, function(index) {
            $(select).append($("<option>", {
                value: countries[index],
                text: countries[index]
            }));
        });  
    });
}

/*
 * Multiplicación, al presionar el botón #multiplicar
 * se hace una llamada al servicio.
 */
function multiply() {
     $("#multiplica").click( function() {
        var num1 = $("#inputm1").val();
        var num2 = $("#inputm2").val();
        $("#multiresult").empty(); //Vaciar el área de resultado.
        if(!isNaN(num1) && !isNaN(num2)) { //Validar los datos de entrada.
            get("/test/multiplication/" + num1 + "/" + num2, function (data) {
                //Desplegar en la vista, agregando información al DOM.
                $("#multiresult").append(data.resultado);
            });
        }
    });
}

/*
 * Palíndromo, al presionar el botón #checapalindromo
 * se hace una llamada al servicio.
 */
function checkPalindrome() {
    $("#checapalindromo").click( function() {
        var input = $("#palinput").val();
        $("#paliresult").empty();
        get("/test/palindrome/" + input, function (data) {
            //Desplegar en la vista, agregando información al DOM.
            if(data.resultado) {
                $("#paliresult").append("Es palindromo");
            } else {
                $("#paliresult").append("No es palindromo");
            }
        });
    });
}

function convertTemp() {
    $("#conviertetemp").click( function() {
        var input = $("#tempinput").val();
        $("#tempresult").empty();
        get("/services/convertTemp/" + input, function (data) {
            $("#tempresult").append(parseFloat(Math.round(data.centigrados * 100) / 100).toFixed(2) + " Cº");
        });
    });
}

function convertMoney() {
    $("#conviertemoneda").click( function() {
        var input = $("#monedainput").val();
        $("#monedaresult").empty();
        get("/services/convertDollars/" + input, function (data) {
            $("#monedaresult").append(parseFloat(Math.round(data.pesos * 100) / 100).toFixed(2) + " MXN");
        });
    });
}

function findCapital() {
    $("#buscacapital").click( function() {
        var input = $("#capitalinput").find(":selected").text();
        $("#capitalresult").empty();
        get("/services/getCapital/" + input, function (data) {
            $("#capitalresult").append(data.capital);
        });
    });
}

function findEmbassy() {
    $("#buscaembajada").click( function() {
        var input = $("#embajadainput").find(":selected").text();
        $("#embajadaresult").empty();
        get("/services/getEmbassy/" + input, function (data) {
            $("#embajadaresult").append(data.latitude + ", " + data.longitude);
        });
    });
}

/*
 * El siguiente código se ejecuta inmediatamente después
 * de que se ha cargado el DOM.
 */
$(document).ready( function() {

    populateSelect("#embajadainput");
    populateSelect("#capitalinput");   

    multiply();
    checkPalindrome();
    convertTemp();
    convertMoney();
    findCapital();
    findEmbassy();
});
