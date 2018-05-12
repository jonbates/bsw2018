package com.mamamend.bsw2018
package client

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.scalajs.dom

import diode.{Effect, EffectSingle}
import io.circe.{Decoder, Json, parser}
import io.circe.generic.auto._

import shared._

object ClientRequests {
  //implicit val trackDecoder: Decoder[Track] = deriveDecoder
  //implicit val bswEventDecoder: Decoder[BswEvent] = deriveDecoder

  def getRequest[T : Decoder](path: String): Future[T] = {
    dom.ext.Ajax.get(
      url = path //,
      //data = data.toString,
      //headers = Map("Content-Type" -> "application/json;charset=utf-8")
    )
    .map(response => {
      parser.parse(response.responseText)
        .fold(l => throw new Exception("can't parse to json"), json => {
          json.as[T]
            .fold(l => throw new Exception("can't parse to expected type"), r => r)
        })
    })   
  }

  def getDevEvents: EffectSingle[SetDevEvents] = {
    Effect(
      getRequest[List[BswEvent]]("/bsw/dev-events")
       .map(events => SetDevEvents(Some(events))))
  }

  def getDataEvents: EffectSingle[SetDataEvents] = {
    Effect(
      getRequest[List[BswEvent]]("/bsw/data-events")
       .map(events => SetDataEvents(Some(events))))
  }
}