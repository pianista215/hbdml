package com.devsmobile.hbdml.bengine.service

import com.devsmobile.hbdml.bengine.models.{BookingConfirmation, BookingRequest}

import scala.concurrent.Future

/**
  * What a Booking Service should provide
  */
trait BookingService {
  def bookHotel(b: BookingRequest): Future[BookingConfirmation]
}
