package com.devsmobile.hbdml.bengine

/**
  * What a Booking Service should provide
  */
trait BookingService {
  def bookHotel(b: BookingRequest): BookingConfirmation
}
