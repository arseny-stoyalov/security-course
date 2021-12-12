package course.checks
import cats.effect.Resource
import course.config.FirewallCheckConfig
import monix.eval.Task

import java.net.URL

class FirewallCheck(config: FirewallCheckConfig) extends Check {

  override def evaluate(): Task[String] = {
    lazy val url = new URL(config.host)
    Resource
      .make(Task.eval(url.openStream))(source => Task.eval(source.close()))
      .use(_ => Task.pure(false))
      .onErrorHandle(_ => true)
      .map {
        case true => "Фаервол корректно работает"
        case false => "Фаервол работает некорректно или не работает"
      }
  }

}
