package com.devsmobile.hbdml.bengine.mongo

import com.devsmobile.hbdml.bengine.models.{BookingRequest, Hotel}
import org.bson.types.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import scala.concurrent.ExecutionContext.Implicits.global //TODO: Change???

import scala.concurrent.Future

object MongoDB {

  private val mongoClient: MongoClient = MongoClient()

  private val hotelsDatabase: MongoDatabase = mongoClient.getDatabase("hotels")

  private val hotels: MongoCollection[Document] = hotelsDatabase.getCollection("details")

  private val bookingsDatabase: MongoDatabase = mongoClient.getDatabase("bookings")

  private val bookings: MongoCollection[Document] = bookingsDatabase.getCollection("bookings")

  def retrieveHotelById(id: String): Future[Hotel] = {
    hotels.find(equal("_id", new ObjectId(id))).first().toFuture.map { document =>
      //TODO: Implicit conversion???
      Hotel(
        document.getObjectId("_id").toString,
        document.getString("city"),
        document.getString("name"),
        document.getDouble("price"),
        document.getInteger("stars"),
        document.getString("image_url")
      )
    }
  }

  //TODO: Store reference to the user!!!!
  def storeBooking(booking: BookingRequest): Future[String] = {
    val id = new ObjectId()
    bookings.insertOne(
      Document(
        "_id" -> id,
        "hotel" -> booking.id,
        "adults" -> booking.adults,
        "childs" -> booking.childs,
        "price" -> booking.price
        )
    ).toFuture.map{ _ =>
      id.toString
    }
  }

}
