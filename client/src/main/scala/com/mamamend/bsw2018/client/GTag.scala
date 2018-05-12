package com.mamamend.bsw2018
package client

import scalajs.js
import scalajs.js.|
import scalajs.js.annotation.{JSGlobalScope}
import japgolly.scalajs.react.Callback

import shared._

@js.native
@JSGlobalScope
object JsGlobals extends js.Object {
  def gtag(command: String, event: String, params: js.Object | js.Dictionary[String]): Unit = js.native
}

object GTag {
  import js.JSConverters._

  @js.native
  trait RawEvent extends js.Object {
    var event_category: String = js.native
    var event_label: String = js.native
    var value: Int = js.native
  }

  object RawEvent {
    def apply(
      eventCategory: Option[String], 
      eventLabel: Option[String], 
      value: Option[Int]): RawEvent = {

      val e = new js.Object().asInstanceOf[RawEvent]
      eventCategory.map(cat => e.event_category = cat)
      eventLabel.map(lab => e.event_label = lab)
      value.map(v => e.value = v)
      e
    }
  }

  def eventCB(action: String, eventCategory: String, eventLabel: Option[String] = None, eventValue: Option[Int] = None): Callback =
    Callback(event(action, Some(eventCategory), eventLabel, eventValue))

  def event(action: String, eventCategory: Option[String]=None, eventLabel: Option[String]=None,
    value: Option[Int]=None) = {
    val params = RawEvent(eventCategory, eventLabel, value)
    JsGlobals.gtag("event", action, params)
  }
}

object BswGTag {
  import GTag._

  def clickBswEvent(event: BswEvent): Callback = {
    println(s"Event clicked: $event")
    val eventValue = event.track match {
      case DevTrack => 0
      case DataTrack => 1
    }
    eventCB("click_bsw_event", "engagement", Some(event.name), Some(eventValue))
  }

}