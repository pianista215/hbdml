package com.devsmobile.hbdml.bengine.service

import com.devsmobile.hbdml.bengine.errors.{HotelBadPriceException, HotelNotFoundException, InvalidAdultsProvided, InvalidChildrenProvided}
import com.devsmobile.hbdml.bengine.models.{BookingConfirmation, BookingRequest, Hotel}
import com.devsmobile.hbdml.bengine.mongo.MongoDB
import scala.concurrent.ExecutionContext.Implicits.global //TODO: Change???

import scala.concurrent.Future

object BookingServiceImpl extends BookingService{

  override def bookHotel(b: BookingRequest): Future[BookingConfirmation] = {
    (for {

      hotelFound <- MongoDB.retrieveHotelById(b.id)
      confirmation <- storeAndConfirmBook(b, hotelFound)

    } yield confirmation) recover {

      case HotelNotFoundException(msg) =>
        BookingConfirmation(false, None, Some(msg))

      case HotelBadPriceException(msg) =>
        BookingConfirmation(false, None, Some(msg))

      case InvalidAdultsProvided(msg) =>
        BookingConfirmation(false, None, Some(msg))

      case InvalidChildrenProvided(msg) =>
        BookingConfirmation(false, None, Some(msg))

      case _ =>
        BookingConfirmation(false, None, Some("Unkown error in server")) //TODO: Log

    }
  }

  private def storeAndConfirmBook(b: BookingRequest, hotel: Hotel): Future[BookingConfirmation] = {
    if(b.city != hotel.city || b.id != hotel.id)
      throw new HotelNotFoundException(s"Hotel not found with id: ${b.id} and city: ${b.city}")
    if(b.price.doubleValue() != hotel.price)
      throw new HotelBadPriceException(s"Problem checking prices for your booking: ${b.price.doubleValue()} != ${hotel.price}")
    if(b.adults <= 0)
      throw new InvalidAdultsProvided(s"You have to choose at least one adult in your booking")
    if(b.childs <0)
      throw new InvalidChildrenProvided(s"You can't book under 0 children")

    MongoDB.storeBooking(b) map { bookingId =>
      BookingConfirmation(true, Some(bookingId))
    }
  }

}
