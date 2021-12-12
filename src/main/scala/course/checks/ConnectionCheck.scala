package course.checks

import course.config.ConnectionCheckConfig
import monix.eval.Task

class ConnectionCheck(config: ConnectionCheckConfig) extends Check {

  def evaluate(): Task[String] =
    Task
      .traverse(config.hosts) { host =>
        ping(host)
      }
      .map {
        case fine if fine.contains(true) => "Компьютер подключен к сети"
        case _ => "Компьютер не подключен к сети"
      }

  private def ping(host: String) =
    Task
      .eval(sys.runtime.exec(s"${config.pingExecutionCommand} $host").waitFor() == 0)
      .timeout(config.timeout)
      .onErrorRecover(_ => false)

}
