package com.devsmobile.hbdml.bengine.models

//TODO: Move Hotel to shared package
case class Hotel(id: String, city: String, name: String, price: Double, stars: Int, imageUrl: String)
case class BookingRequest(id: String, city: String, adults: Int, childs: Int, price: BigDecimal)
case class BookingConfirmation(success: Boolean, id: Option[String], msg: Option[String] = None)