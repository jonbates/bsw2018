package com.mamamend.bsw2018
package server

import java.nio.file.Paths
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect._
import cats.implicits._
import fs2.{Scheduler, Stream, StreamApp}
import fs2.StreamApp.ExitCode
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.headers.{Host, Location}
import org.http4s.server._
import org.http4s.server.blaze._
import org.http4s.server.middleware._
import org.http4s.server.syntax._
import org.http4s.server.SSLKeyStoreSupport.StoreInfo
import org.http4s.twirl._
import org.http4s.Uri.{Authority, RegName, Scheme}

import shared._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._


object Main extends StreamApp[IO] {

  val events = List(
    BswEvent(0, "Office Hours: Ask a CTO", DevTrack),
    BswEvent(1, "Hipster Hosting: Where do you host your code nowadays?", DevTrack),
    BswEvent(2, "Data Science Un-Event", DataTrack),
    BswEvent(3, "Data Engineering & Strategy", DataTrack))

  val webService = HttpService[IO] {
    case req @ GET -> Root / "bsw" =>
      Ok(html.index("<Your GTag Id Here>"))  // Put your google analytics tracking id here

    case req @ GET -> _ if req.pathInfo.startsWith("/assets/") =>
      val path = req.pathInfo.substring("/assets/".length)        
      static(path, req)

    case req @ GET -> Root / "bsw" / "dev-events" =>
      Ok(events.filter(_.track == DevTrack).asJson)      
      
    case req @ GET -> Root / "bsw" / "data-events" =>
      Ok(events.filter(_.track == DataTrack).asJson)  
  }

  def static(file: String, request: Request[IO]): IO[Response[IO]] = {
    StaticFile.fromResource("/public/" + file, Some(request)).getOrElseF(NotFound())
  }

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {
    BlazeBuilder[IO]
      .mountService(webService, "/")
      .bindHttp(8080, "localhost")
      .serve
  }

}
