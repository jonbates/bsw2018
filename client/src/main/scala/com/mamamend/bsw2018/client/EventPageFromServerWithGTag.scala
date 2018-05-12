package com.mamamend.bsw2018
package client

import diode.react.ReactConnectProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._

import com.mamamend.bsw2018.shared._

object EventPageFromServerWithGTag {

  val storeConn: ReactConnectProxy[Store] = AppCircuit.connect(x => x)

  def getData: Callback = Callback {
    AppCircuit.dispatch(GetDevEvents)
    AppCircuit.dispatch(GetDataEvents)
  }

  val component = ScalaComponent.builder[Unit]("EventPageFromServerWithGTag")
    .renderStatic {
      <.div(
        ^.cls := "container",
        <.h3(
          ^.cls := "text-center my-4",
          "Boulder Startup Week"
        ),
        <.p(
          ^.cls := "text-center my-2",
          "Data fetched from server and clicks sent to Google Analytics"
        ),
        storeConn(store =>        
          <.div(
            ^.cls := "row",            
            <.div(
              ^.cls := "col-6",
              store().devEvents.map(devList => 
                EventListGTag("Dev Events", devList))
            ),
            <.div(
              ^.cls := "col-6",
              store().dataEvents.map(dataList => 
                EventListGTag("Data Events", dataList))
            )
          )
        )
      )
    }
    .componentDidMount(_ => getData)
    .build
    
  def apply(): Unmounted[Unit, Unit, Unit] =
    component()
}