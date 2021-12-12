package course

import course.config.Config
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.CanBlock

object Application extends App {

  private lazy val config = Config.load()

  private lazy val checksHandler = new ChecksHandler(config)
  private lazy val exportHandler = new ExportHandler(config.exportPath)
  private lazy val helpHandler = new HelpHandler(config.commandDescriptions)

  private val sc: Scheduler = Scheduler.io("main-scheduler")

  private val argsSet = args.toSet

  val processCommands =
    argsSet.excl(exportHandler.exportKey) match {
      case helpNeeded if helpNeeded.contains(helpHandler.helpKey) =>
        helpHandler.renderHelpRef(checksHandler.availableCommandsKeys + exportHandler.exportKey)
      case commands => checksHandler.evaluateChecks(commands.toList)
    }

  val export: String => Task[String] = txt =>
    if (argsSet.contains(exportHandler.exportKey))
      exportHandler.export(txt)
    else Task.pure(txt)

  processCommands
    .flatMap(export)
    .onErrorHandleWith {
      case unknownCommand: IllegalArgumentException =>
        Task.pure(
          s"Неизвестная команда ${unknownCommand.getMessage}, введите флаг help для вывода списка доступных команд"
        )
      case other => Task.raiseError(other)
    }
    .map(println)
    .runSyncUnsafe()(sc, implicitly[CanBlock])

}
