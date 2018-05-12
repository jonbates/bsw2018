package com.mamamend.bsw2018
package client

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel, JSExportAll}

import japgolly.scalajs.react._

import shared.{BswEvent, DevTrack, DataTrack}


@JSExportTopLevel(name="bsw2018") @JSExportAll
object Client {

  def exampleOne(): Unit = {
    cleanUp

    val listItems = (0 to 10)
      .filter(_ % 2 == 0)
      .map(x => s"<li>$x</li>")
      .mkString("\n")

    dom.document.getElementById("root").innerHTML = s"""
      |<h3>Hello Boulder!</h3>
      |<hr>
      |<h5>Evens:</h5>
      |<ul>
      |$listItems
      |</ul>
      |<hr>""".stripMargin
  }

  def exampleTwo(): Unit = {
    cleanUp
    EventPage().renderIntoDOM(dom.document.getElementById("root"))
  }

  def exampleThree(): Unit = {
    cleanUp
    EventPageFromServer().renderIntoDOM(dom.document.getElementById("root"))
  }

  def exampleFour(): Unit = {
    cleanUp
    EventPageFromServerWithGTag().renderIntoDOM(dom.document.getElementById("root"))
  }

  def cleanUp(): Unit = {
    println("Running clean up...")
    val rootElement = dom.document.getElementById("root")
    println("React unmounting result " + ReactDOM.unmountComponentAtNode(rootElement))
    rootElement.innerHTML = ""    
  }
}