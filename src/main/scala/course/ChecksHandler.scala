package course

import course.checks.{AntiMalwareCheck, Check, ConnectionCheck, FirewallCheck, HasAntiMalwareCheck, HasFirewallCheck}
import course.config.Config
import monix.eval.Task

class ChecksHandler(config: Config) {

  private val availableCommands: Map[String, Check] =
    Map(
      "connection" -> new ConnectionCheck(config.connectionCheck),
      "hasFirewall" -> new HasFirewallCheck(config.firewall),
      "firewall" -> new FirewallCheck(config.firewall),
      "hasAntiMalware" -> new HasAntiMalwareCheck(config.antiMalware),
      "antiMalware" -> new AntiMalwareCheck(config.antiMalware)
    )

  def evaluateChecks(commands: List[String]) =
    Task.traverse(commands) {
      case known if availableCommands.contains(known) => availableCommands(known).evaluate()
      case unknown => Task.raiseError(new IllegalArgumentException(unknown))
    }.map(renderEvaluationResult)

  def availableCommandsKeys: Set[String] = availableCommands.keys.toSet

  private def renderEvaluationResult(list: List[String]) = {
    list.mkString("\n----------------------------------------\n")
  }

}
