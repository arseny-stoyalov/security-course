package course.checks
import course.config.FirewallCheckConfig
import monix.eval.Task

import java.nio.file.{Files, Paths}

class HasFirewallCheck(config: FirewallCheckConfig) extends Check {

  override def evaluate(): Task[String] =
    Task.eval {
      if (Files.exists(Paths.get(config.path)))
        "Фаервол установлен"
      else "Фаервол не установлен"
    }

}
