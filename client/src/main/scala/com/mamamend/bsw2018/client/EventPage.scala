package com.mamamend.bsw2018
package client

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._

import com.mamamend.bsw2018.shared._

object EventPage {

  val devList = List(
    BswEvent(0, "Office Hours: Ask a CTO", DevTrack),
    BswEvent(1, "Hipster Hosting: Where do you host your code nowadays?", DevTrack))

  val dataList = List(
    BswEvent(2, "Data Science Un-Event", DataTrack),
    BswEvent(3, "Data Engineering & Strategy", DataTrack))

  val component = ScalaComponent.builder[Unit]("EventPage")
    .renderStatic {
      <.div(
        ^.cls := "container",
        <.h3(
          ^.cls := "text-center my-4",
          "Boulder Startup Week"
        ),
        <.div(
          ^.cls := "row",
          <.div(
            ^.cls := "col-6",
            EventList("Dev Events", devList)            
          ),
          <.div(
            ^.cls := "col-6",
            EventList("Data Events", dataList)
          )
        )
      )
    }
    .build
    
  def apply(): Unmounted[Unit, Unit, Unit] =
    component()
}