package com.mamamend.bsw2018
package client

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import diode._
import diode.react.ReactConnector
import japgolly.scalajs.react.Callback
import org.scalajs.dom
import shared._

case class Store(devEvents: Option[List[BswEvent]], dataEvents: Option[List[BswEvent]])

case object GetDevEvents extends Action
case object GetDataEvents extends Action
case class SetDevEvents(devEvents: Option[List[BswEvent]]) extends Action
case class SetDataEvents(dataEvents: Option[List[BswEvent]]) extends Action

object AppCircuit extends Circuit[Store] with ReactConnector[Store] {
  def initialModel: Store = Store(
    devEvents = Option.empty[List[BswEvent]], 
    dataEvents = Option.empty[List[BswEvent]])

  override val actionHandler: HandlerFunction = composeHandlers(
    new StoreHandler(zoomRW(identity)((m, t) => t)))
}


class StoreHandler[M](modelRW: ModelRW[M, Store]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case GetDevEvents =>
      effectOnly(ClientRequests.getDevEvents)

    case GetDataEvents =>
      effectOnly(ClientRequests.getDataEvents)

    case SetDevEvents(devEvents) =>
      updated(value.copy(devEvents = devEvents))
    case SetDataEvents(dataEvents) =>
      updated(value.copy(dataEvents = dataEvents))    
  }
}