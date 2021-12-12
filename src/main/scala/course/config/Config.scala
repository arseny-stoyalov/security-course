package course.config

import pureconfig.module.yaml.YamlConfigSource
import pureconfig.generic.auto._

case class Config(
  connectionCheck: ConnectionCheckConfig,
  antiMalware: AntiMalwareCheckConfig,
  firewall: FirewallCheckConfig,
  exportPath: String,
  commandDescriptions: Map[String, String]
)

object Config {

  def load(): Config =
    YamlConfigSource
      .file("/Users/a1/Documents/JavaProjects/myprjcts/security-course/src/main/resources/application.yaml")
      .load[Config]
      .fold(f => throw new Exception(f.prettyPrint()), s => s)

}
