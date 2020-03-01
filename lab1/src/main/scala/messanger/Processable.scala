package messanger

import messanger.messages.{LogoutMessage, Message}

trait Processable[A] extends Runnable {
  var isRunning = true;

  def readObjectAndSender: (AnyRef, A)

  def processMessage(message: Message, sender: A): Unit

  def processLogout(sender: A): Unit

  def close: Unit

  override def run(): Unit = {
    try {
      while (isRunning)
        processMessages
    } catch {
      case e: Exception => e.printStackTrace; close
    }
  }

  def processMessages: Unit = {
    val (received, sender) = readObjectAndSender
    received match {
      case mess: Message =>
        processMessage(mess, sender)
      case mess: LogoutMessage =>
        processLogout(sender)
        isRunning = false
    }

  }
}
