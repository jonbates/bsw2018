package com.mamamend.bsw2018
package client

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.html_<^._

import com.mamamend.bsw2018.shared._

object EventListGTag {

  case class Props(title: String, events: List[BswEvent])

  val component = ScalaComponent.builder[Props]("EventListGTag")
    .render_P(props => {
      <.div(
        <.h5(
          ^.cls := "text-center",
          props.title
        ),
        <.ul(
          ^.cls := "border list-unstyled",
          props.events.toTagMod(event => {
            <.li(
              ^.cls := "bg-secondary text-light text-center border",
              ^.onClick --> BswGTag.clickBswEvent(event),
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