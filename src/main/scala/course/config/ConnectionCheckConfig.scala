package course.config

import scala.concurrent.duration.FiniteDuration

case class ConnectionCheckConfig(pingExecutionCommand: String, hosts: List[String], timeout: FiniteDuration)
