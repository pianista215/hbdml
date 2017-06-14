package com.devsmobile.hbdml.bengine

case class BookingRequest(id: String, city: String, price: BigDecimal)
case class BookingConfirmation(success: Boolean, id: String, msg: String)