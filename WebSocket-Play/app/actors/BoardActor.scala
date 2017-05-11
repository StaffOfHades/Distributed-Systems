package actors

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.event.LoggingReceive
import akka.actor.ActorRef
import akka.actor.Terminated
import play.libs.Akka
import akka.actor.Props

class BoardActor extends Actor with ActorLogging {
    var users = Set[ActorRef]()

    /*
     * Cuando el Board Actor recibe un mensaje, escoge entre tres opciones:
     * si es un mensaje de texto, se lo envía a todos los UserActor suscriptos;
     * si es para subscribirse, los agrega, y empieza monitorear;
     * si es para terminar la suscripción, invalida la suscripción del usuario.
     */
    def receive = LoggingReceive {
        case m:Message => users map { _ ! m} //Recibe el mensaje y lo envía a los actores suscritos
        case Subscribe => {
            users += sender // Agregar a usuarios al sender.
            context watch sender // Observar al sender
        }
        case Terminated(user) => users -= user
    }
}

/*
 * Define un singleton para BoardActor, que lo regresa
 * siempre que un UserActor se suscribe, y solo se crea una vez.
 */
object BoardActor {
    lazy val board = Akka.system().actorOf(Props[BoardActor])
    def apply() = board // Cuando un actor se suscribe retorna Board.
}

case class Message(uuid: String, s: String)
object Subscribe
