# Scala.js + Scala examples for Boulder Startup Week 2018

### Workflow
* `sbt` and then `~compile` for compilation on changes
* `sbt` and run `project client` then `fastOptJS::startWebpackDevServer` to server out assets on localhost:8081
* `sbt` and then `run` to start webserver at localhost:8081/bsw
* Goto http://localhost:8081/bsw then run examples in the javascript console
    * `bsw2018.exampleOne()` -- setting innerHTML of a div to html string
    * `bsw2018.exampleTwo()` -- using basic React components
    * `bsw2018.exampleThree()` -- using React components with data from the server
    * `bsw2018.exampleFour()` -- using GTag to tag analytics events

### Notes
* The GTag example won't work unless you set your GTag id in the server project Main.scala file
