package course

import monix.eval.Task

import java.nio.file.{Files, Paths}

class ExportHandler(path: String) {

  val exportKey: String = "export"

  def export(text: String): Task[String] = {
    Task.eval(Files.write(Paths.get(path), text.getBytes()))
      .as(text)
  }

}
