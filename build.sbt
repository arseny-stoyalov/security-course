name := "security-course"

version := "0.1"

scalaVersion := "2.13.7"

lazy val catsDeps = Seq(
  "org.typelevel" %% "cats-core" % "2.6.1",
  "org.typelevel" %% "cats-kernel" % "2.6.1",
  "org.typelevel" %% "cats-effect" % "2.5.3"
)

libraryDependencies ++=
  catsDeps ++ Seq(
    "io.monix" %% "monix" % "3.4.0",
    "com.github.pureconfig" %% "pureconfig" % "0.12.3"
  )

