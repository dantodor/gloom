name := "gloom"

organization := "lorcatech"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

libraryDependencies ++= Seq(
  "org.typelevel"  %% "cats-core"  % "1.0.0-RC1",
  "org.scalatest"  %% "scalatest"  % "3.0.4"  % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)

