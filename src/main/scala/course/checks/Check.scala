package course.checks

import monix.eval.Task

trait Check {

  def evaluate(): Task[String]

}
