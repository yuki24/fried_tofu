import com.typesafe.startscript.StartScriptPlugin

organization := "net.yuki24"

name := "fried_tofu"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.3",
  "org.scalatra" %% "scalatra-scalate" % "2.0.3",
  "org.scalatra" %% "scalatra-specs2" % "2.0.3" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.0" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "7.5.4.v20111024" % "container",
  "org.eclipse.jetty" % "jetty-server" % "7.5.4.v20111024" % "container",
  "org.eclipse.jetty" % "jetty-webapp" % "7.5.4.v20111024",
  "org.eclipse.jetty" % "jetty-server" % "7.5.4.v20111024",
  "javax.servlet" % "servlet-api" % "2.5" % "provided",
  "org.im4java" % "im4java" % "1.2.0",
  "com.amazonaws" % "aws-java-sdk" % "1.3.3"
)

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

seq(StartScriptPlugin.startScriptForClassesSettings: _*)
