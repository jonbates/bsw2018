package com.mamamend.bsw2018
package shared

case class BswEvent(id: Int, name: String, track: Track)

sealed trait Track
final case object DevTrack extends Track
final case object DataTrack extends Track