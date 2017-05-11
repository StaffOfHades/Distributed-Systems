var ws;
var speech = ["Hello there!","Welcome to the world of Pokémon!",
              "My name is Oak!","People call me the Pokémon Prof!",
              "This world is inhabited by creatures called Pokémon!",
              "For some people, Pokémon are pets",
              "Other use them for fights",
              "Myself… I study Pokémon as a profession",
              "First, what is your name?"];
var interval;
var index = 0;

/*
 * Se profesor manda un mensaje en el chat,
 * hasta que no tiene nada mas q decir.
 */
function professor() {
    sendMsg(speech[index++]);
    if( index >= speech.length ) {
        clearInterval(interval);
    }   
}

/*
 * Iniciamos la conexión con websocket hacia el servidor
 * Cuando un mensaje es recibido se hace un parsing del JSON
 * Se obtiene el mensaje y se agrega a la vista.
 */
function open() {
    //Se toma La URL del Tag en Body
    ws = $("body").data("ws-url");
    console.log(ws);
    ws = new WebSocket(ws);

    // Cuando el ws regresa un mensaje, muestralo.
    ws.onmessage = function(event) {
        var message;
        
        // Descompone el mensaje
        message = JSON.parse(event.data);
        console.log(message);

        // Si el mensaje fue enviado correctamente, parsealo.
        // Si no, envialo a consola.
        switch (message.type) {
            case "message":
                // Cuando el servidor envía de regreso un mensaje,
                // lo muestra en tres columnas diferentes:
                // una para el usuario, otro para el mensaje, y otro para la hora.
                return $("#board tbody").append("<tr><td class=\"user col-xs-3\">Usuario#" + 
                    message.uid + " dice:</td class=\"col-xs-3\"><td>"+ message.msg +
                    "</td><td class=\"timestamp col-xs-3\">" + message.timestamp + "</td></tr>");
            default:
                return console.log("Unexpected \"" + message + "\"");
        }
    };
}

/*
 * Envia un mensaje en respuesta a una accion del usuario. 
 */
function submit() {
    $("#msgform").submit(function(event) {
        //console.log("Clicked");
        event.preventDefault();
        console.log("Msg: " + $("#msgtext").val());
        sendMsg( $("#msgtext").val() );
        return $("#msgtext").val("");
    });
}

/* 
 * Envia el mensaje en el formulario como JSON a traves del ws.
 */
function sendMsg(msg) {
    ws.send( JSON.stringify( { msg: msg } ) );
}

/*
 * Cuando el documento se haya cargado,
 * abre el ws y habilita el boton.
 */
$(document).ready( function() {
    open();
    submit();
    //interval = setInterval(professor, 5000);
});
