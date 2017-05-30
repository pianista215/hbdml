package com.devsmobile.hbdml.mongo

import com.devsmobile.hbdml.models.Hotel
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.model.Sorts._

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global //TODO

object MongoDB {

  private val mongoClient: MongoClient = MongoClient()

  private val database: MongoDatabase = mongoClient.getDatabase("hotels")

  private val hotels: MongoCollection[Document] = database.getCollection("details")

  def retrieveHotels: Future[Seq[Hotel]] = {
    hotels.find().sort(ascending("price")).limit(10).toFuture.map { documents =>
        //TODO: Implicit conversion???
      documents map { document =>
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
  }
}


