lazy val commonSettings = Seq(
  scalaVersion := scalaV,
  // If not using ensime comment out these two
  //ensimeScalaVersion in ThisBuild := scalaV,
  //ensimeIgnoreMissingDirectories in ThisBuild := true,
  scalacOptions in ThisBuild ++= Seq("-Ypartial-unification", "-deprecation", "-feature"))

val scalaV = "2.12.6"
val autowireVersion = "0.2.1"
val catsVersion = "1.1.0"
val catsEffectVersion = "0.10.1"
val circeVersion = "0.9.3"
val diodeVersion = "1.1.3"
val diodeReactVersion = "1.1.3.120"
val doobieVersion = "0.5.2" 
val scalaJsDomVersion = "0.9.5"
val scalaJsReactVersion = "1.2.0"
val reactVersion = "16.3.1"
val http4sVersion = "0.18.11"

scalaVersion in ThisBuild := scalaV

lazy val server = (project in file("server"))
  .settings(commonSettings: _*)
  .settings(
    scalaJSProjects := Seq(client), 
    webpackBundlingMode := BundlingMode.LibraryAndApplication(), 
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value,
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      "ch.qos.logback"  % "logback-classic"       % "1.2.3",
      "org.tpolecat"    %% "doobie-core"          % doobieVersion,
      "org.tpolecat"    %% "doobie-postgres"      % doobieVersion,
      "org.tpolecat"    %% "doobie-specs2"        % doobieVersion,
      "org.tpolecat"    %% "doobie-scalatest"     % doobieVersion,
      "org.tpolecat"    %% "doobie-hikari"        % doobieVersion,
      "io.circe"        %% "circe-core"           % circeVersion,
      "io.circe"        %% "circe-generic"        % circeVersion,
      "io.circe"        %% "circe-parser"         % circeVersion,
      "org.http4s"      %% "http4s-blaze-server"  % http4sVersion,
      "org.http4s"      %% "http4s-blaze-client"  % http4sVersion,
      "org.http4s"      %% "http4s-circe"         % http4sVersion,
      "org.http4s"      %% "http4s-dsl"           % http4sVersion,
      "org.http4s"      %% "http4s-twirl"         % http4sVersion
    ))
  .enablePlugins(SbtTwirl, WebScalaJSBundlerPlugin)
  .dependsOn(sharedJvm) 

lazy val client = (project in file("client"))
  .settings(commonSettings: _*)
  .settings(
  //webpackDevServerExtraArgs := Seq("--https", "--cert", s"$homeDir/mm-web/ssl-web/cert.pem", 
  //  "--key", s"$homeDir/mm-web/ssl-web/privkey.pem", "--public", "dev.mamamend.com:8081", "--inline", "--watch"),
  webpackDevServerPort := 8081,
  scalaJSUseMainModuleInitializer := false,
  webpackBundlingMode := BundlingMode.LibraryAndApplication(),
  libraryDependencies ++= Seq(
    "org.scala-js"                      %%% "scalajs-dom"    % scalaJsDomVersion,
    "com.github.japgolly.scalajs-react" %%% "core"           % scalaJsReactVersion,
    "io.suzaku"                         %%% "diode"          % diodeVersion,
    "io.suzaku"                         %%% "diode-devtools" % diodeVersion,
    "io.suzaku"                         %%% "diode-react"    % diodeReactVersion,
    "io.circe"                          %%% "circe-core"     % circeVersion,
    "io.circe"                          %%% "circe-generic"  % circeVersion,
    "io.circe"                          %%% "circe-parser"   % circeVersion
  ),
  jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
  npmDependencies in Compile ++= Seq(
     "react-dom" -> reactVersion,
     "react" -> reactVersion))
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb, ScalaJSBundlerPlugin)
  .dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Full) in file("shared"))
  .settings(commonSettings: _*)
  .enablePlugins(ScalaJSPlugin)
  .jsConfigure(_ enablePlugins ScalaJSWeb)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// loads the server project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
