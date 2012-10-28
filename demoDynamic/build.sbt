scalaVersion := "2.10.0-RC1"


scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:postfixOps")


libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M1" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.0-RC1"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.10.0-RC1"

libraryDependencies += "org.scalaz" % "scalaz-core_2.10.0-M7" % "7.0.0-M3"



libraryDependencies += "net.sf.opencsv" % "opencsv" % "2.3"
