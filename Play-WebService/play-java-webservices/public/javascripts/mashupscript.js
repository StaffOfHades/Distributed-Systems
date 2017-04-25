let server = "localhost"
let port = ":9000"
var clicked = false;
let mapZoom = 6;
let flickrRadius = 0.1;
let maxImgs = 4;


/* Ajax GET request. Recieves the url to query,
 * as well as the data type it receives.
 * Finally, this function recieved another function 
 * that receives the response.
 */
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

/* Queries the server to convert Kelvin temperature
 * into Celsius. This function receives the input temp,
 * as well as the function to which the final result is passed.
 * Before the temp is returned, it is unpacked, and parsed.
 */
function convertTemp(input, callback) {
    let url = "http://" + server + port + "/services/convertTempK/" + input;
    get(url, "json", function(data) {
        let result  = parseFloat(Math.round(data.centigrados * 100) / 100).toFixed(2) + " Cº";
        callback(result);
    });
}

/* Queries the server to find the capital of a country.
 * This function receives the country name as input,
 * as well as the function to which the final result is passed.
 * The result is simply unpacked before being returned.
 */
function getCapital(input, callback) {
    let url = "http://" + server + port + "/services/getCapital/" + input;
    get(url, "json", function(data) {
        callback(data.capital);
    });
}

/* Queries the server to find the geolocation of the Mexican.
 * Embassy in the target country. This function receives 
 * the country name as input, as well as the function 
 * to which the final result is passed.
 * The result is simply unpacked before being returned.
 */
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

            // Llama a los servicios, y los muestra.
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
    // Se crea el objeto Geocoder del api de Goole Maps
    let geocoder = new google.maps.Geocoder();
    // Se realiza el query para el pais
    geocoder.geocode( { "address": country, "region": "world" }, function(results, status) {
        // Se guardan las coordenadas
        let longitude = results[0].geometry.location.lng();
        let latitude  = results[0].geometry.location.lat();
        // Y se crea la vista a mostrar en el mapa.
        let mapProp = {
            center: new google.maps.LatLng(latitude,longitude),
            zoom: mapZoom,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        // Llamamos al mapa y lo agregamos al div #map
        let map = new google.maps.Map($("#map").get(0), mapProp);

        // Se realiza un query de la embajada, y se agrega al mapa
        findEmbassy(name, function (latitude, longitude) {
            // Se crea el puntero a mostrar basado en las coordenadas dadas.
            let marker = new google.maps.Marker( {
                position: {lat: latitude, lng: longitude},
                map: map,
                title: "Embajada Mexicana"
            });
        });
    });
}
    
/* Llamar asíncronamente la API REST de OpenWeather.
 * Una vez obtenido el resultado, realiza una llamda al server para convertirla,
 * y lo muestra a lado del mapa dentro de un circulo.
 */
function showWeather(country) {
    let key = "APPID=ee581a64db6545b73b855fc5f5afbadc";
    let url = "http://api.openweathermap.org/data/2.5/weather?" + key + "&q=" + country;
    get(url, "json", function (data) {
        let max = data.main.temp_max;
        let min = data.main.temp_min;
        let des = data.weather[0].description;
        
        // Convierte primero una temperature        
        convertTemp(max, function (result) {
            
            // Desplegar la vista, agregando componentes en el DOM.
            $("#clima").append("<h3 class=\"accent\">El Clima</h3>");
            $("#clima").append("<ul>");
            $("#clima").append("<li class=\"accent\">máx. " + result + "</li>");

            // Y despues la otra para garantizar un orden.
            convertTemp(min, function (result) {
                $("#clima").append("<li class=\"accent\">mín. " + result + "</li>");
                $("#clima").append("<li class=\"accent\">" + des + "</li>");
                $("#clima").append("</ul>");
            }); 
        });
        
    });
}

/* Encontrar la locazion de la embajada, y con ella 
 * llamar asíncronamente la API REST de Flickr,
 * para mostrar imagenes cercanas.
 */
function showImgs(country, name) {
    let key = "e327d4c00dbfc6a3ffcdc8a4bb709a0d";
    findEmbassy(name, function (latitude, longitude) {
        // Encontrar la imagens interesantes en un rango de las cordenas dadas
        let flickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                        key /* + "&tags=" + country + "+city"*/ + "&sort=interestingness-desc" +
                        "&lat=" + latitude + "&lon=" + longitude + "&radius=" + flickrRadius +
                        "&format=json&jsoncallback=?";
        console.log("url: " + flickrURL);
        var src;
        // Recuperar las imagens, e iterar sobre ellas
        $.getJSON(flickrURL, function(data) {
            $.each(data.photos.photo, function(i, item) {
                src = "http://farm" + item.farm + ".static.flickr.com/" +
                        item.server + "/" + item.id + "_" + item.secret + "_m.jpg";
                // Desplegar la vista, agregando componentes en el DOM.
                $("<img/>").attr("src", src).appendTo("#images");
                // Cuando se llega al limite de imagnes, regresar.
                if ( i == maxImgs ) return false;
            });
        });
    });
}

/* Llamar asíncronamente la API REST de Wikipedia,
 * y agregar la informacion al DOM
 */
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
