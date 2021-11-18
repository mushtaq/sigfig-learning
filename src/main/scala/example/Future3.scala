package example

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}
import async.Async._

object Future3 {

  def main(args: Array[String]): Unit = {

    val f1              = Future(100)
    val f2: Future[Int] = Future(throw new RuntimeException("abc")).recover { case NonFatal(ex) =>
      0
    }

    val f3: Future[Int] = f1
      .flatMap { x =>
        f2.map { y =>
          x + y
        }
      }
      .recover { case NonFatal(ex) =>
        0
      }

    val f4: Future[Int] = {
      for {
        x <- f1
        y <- f2
      } yield x + y
    }.recover { case NonFatal(ex) =>
      0
    }

    val f5 = async {
      await(f1) + await(f2)
    }.recover { case NonFatal(ex) =>
      0
    }

    f3.onComplete(println)
    f4.onComplete(println)
    f5.onComplete(println)

    // prevent main from exiting
    Thread.sleep(Int.MaxValue)
  }

}
