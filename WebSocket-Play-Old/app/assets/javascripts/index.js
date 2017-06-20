/*
 * Aquí se escribirá código JavaScript puro, para conectarse al WebSocket
 */

/*
 * Iniciamos la conexión con websocket hacia el servidor
 * Cuando un mensaje es recibido se hace un parsing del JSON
 * Se obtiene el mensaje y se agrega a la vista.
 */
var ws;
ws = new WebSocket($("body").data("ws-url")); //Se toma La URL del Tag en Body
ws.onmessage = function(event) {
    var message;
    message = JSON.parse(event.data);
    switch (message.type) {
        case "message":
            return $("#board tbody").append("<tr><td>"+ message.msg + "</td></tr>");
        default:
            return console.log(message);
    }
};
$("#msgform").submit(function(event) {
    event.preventDefault();
    console.log($("#msgtext").val());
    ws.send(JSON.stringify({ //Enviar el mensaje en el formulario como JSON.
        msg: $("#msgtext").val()
    }));
    return $("#msgtext").val("");
});
