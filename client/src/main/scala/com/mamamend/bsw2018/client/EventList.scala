package com.mamamend.bsw2018
package client

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._

import com.mamamend.bsw2018.shared._

object EventList {

  case class Props(title: String, events: List[BswEvent])

  val component = ScalaComponent.builder[Props]("EventList")
    .render_P(props => {
      <.div(
        <.h5(
          props.title
        ),
        <.ul(
          ^.cls := "border",
          props.events.toTagMod(event => {
            <.li(
              event.name
            )
          })
        )
      )
    })
    .build
    
  def apply(title: String, events: List[BswEvent]): Unmounted[Props, Unit, Unit] =
    component(Props(title, events))
}