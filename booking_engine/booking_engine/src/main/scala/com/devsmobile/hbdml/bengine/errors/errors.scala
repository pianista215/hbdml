package com.devsmobile.hbdml.bengine.errors

case class HotelNotFoundException(message: String) extends Exception
case class HotelBadPriceException(message: String) extends Exception
case class InvalidAdultsProvided(message: String) extends IllegalArgumentException
case class InvalidChildrenProvided(message: String) extends IllegalArgumentException
