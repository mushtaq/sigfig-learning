package example

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

sealed trait I
case class A() extends I
case class B() extends I

object Future2 {

  def main(args: Array[String]): Unit = {

    val runtimeValue = false

    val x: Try[Int] = if (runtimeValue) Try(10 / 2) else Try(10 / 0)

    println(x)

    x match {
      case Failure(exception) => println(exception.getMessage)
      case Success(value)     => println(value)
    }

    Future(10 / 0).onComplete(println)
    Future(10 / 0).onComplete {
      case Failure(exception) => println(exception.getMessage)
      case Success(value)     => println(value)
    }

    // prevent main from exiting
    Thread.sleep(Int.MaxValue)
  }

}
