let server = "localhost"
let port = ":9000"
var clicked = false;

function get(url, type, result) {
    console.log("url: " + url);
    $.ajax( {
        type: "GET",
        url: url,
        data: { get_param: "value" },
        dataType: type,
        success: result
    });
} 

function convertTemp(input, callback) {
    var url = "http://" + server + port + "/services/convertTempK/" + input;
    get(url, "json", callback);
}

function getCapital(input, callback) {
    var url = "http://" + server + port + "/services/getCapital/" + input;
    get(url, "json", callback);
}

function findEmbassy(input, callback) {
    var url = "http://" + server + port + "/services/getEmbassy/" + input;
    get(url, "json", callback);
}

/*
 * El siguiente evento remueve del DOM #message al hacer
 * click en #close
 */
function close() {
    $("#close").click( function() {
        $("body").find("div:first-child").remove();
        if (clicked) $("#button").trigger("click");
    });
}

/*
 * El siguiente evento activa las llamadas a los servicio y despliega la interfaz
 * al hacer click en el botón "consultar".
 */
function showCountry() {
    $("#button").click( function() {
        clicked = true;
        var country = $("#select").val(); //Obtener value de cada <option> en <select>
        var selectText = $("#select option:selected").text(); //obtener el texto de la opción seleccionada.

        //Vaciar el div #app cada vez que se presione el botón.
        $("#app").empty();

        //Añadir los contenedores de cada sección al div #app
        getCapital(selectText, function (data) {
            var capital = data.capital; 
            //Poner el nombre del país de acuerdo al texto de <select>
            $("#app").append("<h1>Estadísticas para: <u id=\"title\">" + selectText + 
                            "," + capital + "</u></h1>");
            $("#app").append("<div id=\"extract\">  </div>");
            $("#app").append("<div id=\"clima\">    </div>");
            $("#app").append("<div id=\"map\">      </div>");
            $("#app").append("<div id=\"images\">   </div>");

            showMap(country, selectText);
            showWeather(country); 
            showImgs(country, selectText);
            showInfo(country);

        }); 
    });
}

/*
 * Aquí se utiliza la API de google maps para obtener latitud y longitud
 * basado en una dirección, en este caso el nombre del país.
 */
function showMap(country, name) {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode( { "address": country, "region": "world" }, function(results, status) {
        var longitude = results[0].geometry.location.lng();
        var latitude  = results[0].geometry.location.lat();
        var mapProp = {
            center: new google.maps.LatLng(latitude,longitude),
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        //Llamamos al mapa y lo agregamos al div #map
        var map = new google.maps.Map($("#map").get(0), mapProp);

        findEmbassy(name, function (data) {
            var marker = new google.maps.Marker( {
                position: {lat: data.latitude, lng: data.longitude},
                map: map,
                title: "Embajada Mexicana"
            });
        });
    });
}
    
//Llamar asíncronamente la API REST de OpenWeather.
function showWeather(country) {
    var key = "APPID=ee581a64db6545b73b855fc5f5afbadc";
    var url = "http://api.openweathermap.org/data/2.5/weather?" + key + "&q=" + country;
    get(url, "json", function (data) {
        var names = data;
        var max = data.main.temp_max;
        var min = data.main.temp_min;
        var des = data.weather[0].description;
         
        convertTemp(max, function (data) {
            var result  = parseFloat(Math.round(data.centigrados * 100) / 100).toFixed(2);
            // Desplegar la vista, agregando componentes en el DOM.
            $("#clima").append("<h3>El Clima</h3>");
            $("#clima").append("<ul>");
            $("#clima").append("<li>máx. " + result + "</li>");
        });
        
        convertTemp(min, function (data) {
            var result  = parseFloat(Math.round(data.centigrados * 100) / 100).toFixed(2);
            $("#clima").append("<li>mín. " + result + "</li>");
            $("#clima").append("<li>" + des + "</li>");
            $("#clima").append("</ul>");
        }); 
    });
}

//Llamar asíncronamente la API REST de Flickr.
function showImgs(country, name) {
    var key = "8682e54c00e1ee915795f57158982fa4"
    var flickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                    key /* + "&tags=" + country + "+city"*/ + "&sort=interestingness-desc";
    findEmbassy(name, function (data) {
        flickrURL = flickrURL + "&lat=" + data.latitude + "&lon=" + data.longitude +
                    "&radius=" + 30 + "&format=json&jsoncallback=?";
        console.log("url: " + flickrURL);
        var src;
        $.getJSON(flickrURL, function(data) {
            $.each(data.photos.photo, function(i,item) {
                src = "http://farm" + item.farm + ".static.flickr.com/" +
                        item.server + "/" + item.id + "_" + item.secret + "_m.jpg";
                //Desplegar la vista, agregando componentes en el DOM.
                $("<img/>").attr("src", src).appendTo("#images");
                if ( i == 3 ) return false;
            });
        });
    });
}

//Llamar asíncronamente la API REST de Wikipedia.
function showInfo(country) {
    var wikipediaURL = "http://en.wikipedia.org/w/api.php" + 
                        "?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + country;
    get(wikipediaURL, "jsonp", function (data) {
        var data = data.query.pages;
        var key = Object.keys(data)[0]; 
        var string = data[key].extract;
        var output = string.slice(0, string.indexOf("."));
        //Desplegar la vista, agregando componentes en el DOM.
        $("#extract").append(output + ".");
    });
}

/*
 * El siguiente código se ejecuta inmediatamente después
 * de que se ha cargado el DOM.
 */
$(document).ready( function() {
    showCountry(); 
    close(); 
});
