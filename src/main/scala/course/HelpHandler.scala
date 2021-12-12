package course

import monix.eval.Task

class HelpHandler(commandsDesc: Map[String, String]) {

  val helpKey: String = "help"

  def renderHelpRef(commands: Set[String]): Task[String] =
    Task.pure(
      commands
        .map { command =>
          s"$command - ${commandsDesc.getOrElse(command, "Описание недоступно")}"
        }
        .toList
        .mkString("\n")
    )

}
