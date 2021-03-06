import actor.client.ClientActor
import actor.server.ServerActor
import message.Query
import akka.actor.{ActorRef, ActorSystem}

object Main1 extends App {
  val system = ActorSystem("SR_system")

  val server = system.actorOf(ServerActor(2), name = "helloactor")
  val clients: List[ActorRef] = List.tabulate(3)(n => system.actorOf(ClientActor(server)));
  var i = 0

  console()

  def console(): Unit = {
    while (true) {
      print(">> ")
      val line = Console.in.readLine().trim.replaceAll(" +", " ")
      i += 1
      clients(i % 3) ! Query(line)
    }
  }
}