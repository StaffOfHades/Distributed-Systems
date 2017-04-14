let server = "localhost"
let port = ":9000"
var clicked = false;
let mapZoom = 6;
let flickrRadius = 0.1;
let maxImgs = 4;

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
    let url = "http://" + server + port + "/services/convertTempK/" + input;
    get(url, "json", function(data) {
        let result  = parseFloat(Math.round(data.centigrados * 100) / 100).toFixed(2) + " Cº";
        callback(result);
    });
}

function getCapital(input, callback) {
    let url = "http://" + server + port + "/services/getCapital/" + input;
    get(url, "json", function(data) {
        callback(data.capital);
    });
}

function findEmbassy(input, callback) {
    let url = "http://" + server + port + "/services/getEmbassy/" + input;
    get(url, "json", function(data) {
        callback(data.latitude, data.longitude);
    });
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
        let country = $("#select").val(); //Obtener value de cada <option> en <select>
        let selectText = $("#select option:selected").text(); //obtener el texto de la opción seleccionada.

        //Vaciar el div #app cada vez que se presione el botón.
        $("#app").empty();

        //Añadir los contenedores de cada sección al div #app
        getCapital(selectText, function (capital) {
            let title = selectText + ", " + capital;
            //Poner el nombre del país de acuerdo al texto de <select>
            $("#app").append("<h1>Estadísticas para: <u class=\"underline\">" + title + "</u></h1>");
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
    let geocoder = new google.maps.Geocoder();
    geocoder.geocode( { "address": country, "region": "world" }, function(results, status) {
        let longitude = results[0].geometry.location.lng();
        let latitude  = results[0].geometry.location.lat();
        let mapProp = {
            center: new google.maps.LatLng(latitude,longitude),
            zoom: mapZoom,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        //Llamamos al mapa y lo agregamos al div #map
        let map = new google.maps.Map($("#map").get(0), mapProp);

        findEmbassy(name, function (latitude, longitude) {
            let marker = new google.maps.Marker( {
                position: {lat: latitude, lng: longitude},
                map: map,
                title: "Embajada Mexicana"
            });
        });
    });
}
    
//Llamar asíncronamente la API REST de OpenWeather.
function showWeather(country) {
    let key = "APPID=ee581a64db6545b73b855fc5f5afbadc";
    let url = "http://api.openweathermap.org/data/2.5/weather?" + key + "&q=" + country;
    get(url, "json", function (data) {
        let max = data.main.temp_max;
        let min = data.main.temp_min;
        let des = data.weather[0].description;
         
        convertTemp(max, function (result) {
            
            // Desplegar la vista, agregando componentes en el DOM.
            $("#clima").append("<h3 class=\"accent\">El Clima</h3>");
            $("#clima").append("<ul>");
            $("#clima").append("<li class=\"accent\">máx. " + result + "</li>");

            convertTemp(min, function (result) {
                $("#clima").append("<li class=\"accent\">mín. " + result + "</li>");
                $("#clima").append("<li class=\"accent\">" + des + "</li>");
                $("#clima").append("</ul>");
            }); 
        });
        
    });
}

//Llamar asíncronamente la API REST de Flickr.
function showImgs(country, name) {
    let key = "8682e54c00e1ee915795f57158982fa4"
    findEmbassy(name, function (latitude, longitude) {
        let flickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                        key /* + "&tags=" + country + "+city"*/ + "&sort=interestingness-desc" +
                        "&lat=" + latitude + "&lon=" + longitude + "&radius=" + flickrRadius +
                        "&format=json&jsoncallback=?";

        console.log("url: " + flickrURL);
        
        var src;
        $.getJSON(flickrURL, function(data) {
            $.each(data.photos.photo, function(i, item) {
                src = "http://farm" + item.farm + ".static.flickr.com/" +
                        item.server + "/" + item.id + "_" + item.secret + "_m.jpg";
                
                //Desplegar la vista, agregando componentes en el DOM.
                $("<img/>").attr("src", src).appendTo("#images");
                if ( i == maxImgs ) return false;
            });
        });
    });
}

//Llamar asíncronamente la API REST de Wikipedia.
function showInfo(country) {
    let wikipediaURL = "http://en.wikipedia.org/w/api.php" + 
                        "?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + country;
    get(wikipediaURL, "jsonp", function (data) {
        let pages = data.query.pages;
        let key = Object.keys(pages)[0]; 
        let string = pages[key].extract;
        let output = string.slice(0, string.indexOf("."));
        
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
