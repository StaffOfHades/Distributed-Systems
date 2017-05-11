package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import akka.event.LoggingReceive
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import java.util.Calendar
import java.text.SimpleDateFormat
import scala.xml.Utility

class UserActor(uid: String, board: ActorRef, out: ActorRef) extends Actor with ActorLogging {

    /*
     * Cuando el UserActor inicia, subscribirse al BoardActor  
     */
    override def preStart() = {
        BoardActor() ! Subscribe
    }

    /*
     * Cuando el UserActor recibe un mensaje, escoge de tres opciones:
     * si es del BoardActor, envía a la salida (ws) el mensaje
     * junto con el id del  que lo envió; si es un JSON,
     * lo valida y envia al BoardActor;
     * en otro caso, lo reporta como error
     */
    def receive = LoggingReceive {
        case Message(muid, s) if sender == board => {
            val today = Calendar.getInstance().getTime();

            // Utilizando el tiempo actual, lo formatea para conseguir
            // la hora, minutos, segundos, milisegundos y el am/pm
            val time = new SimpleDateFormat("hh:mm:s:S a").format(today);

            // El JSON regresa el mensaje, el emisor, y el tiempo en que se creo.
            val js = Json.obj("type" -> "message", "uid" -> muid, "msg" -> s, "timestamp" -> time)
            out ! js
        }
        case js: JsValue => (js \ "msg").validate[String] map { Utility.escape(_) } map { board ! Message(uid, _ ) }
        case other => log.error("Error, no se creó el objeto JSON: " + other)
    }
}

/*
 * Define un método constructor para UserActor, que recibe un id, y la salida(ws).
 */
object UserActor {
    def props(uid: String)(out: ActorRef) = Props(new UserActor(uid, BoardActor(), out))
}
