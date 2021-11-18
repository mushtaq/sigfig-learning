package example

import scala.concurrent.Future

object EitherFuture {
  import scala.concurrent.ExecutionContext.Implicits.global

  def main(args: Array[String]): Unit = {
    val successResult: Future[Either[String, Int]] = Future.successful(Right(100))
    val errorResult: Future[Either[String, Int]]   = Future.successful(Left("something went wrong!"))
    val errorResult2: Future[Either[String, Int]]  = Future.failed(new RuntimeException("abc"))

    println(successResult)
    println(errorResult)
    println(errorResult2)

    println("****************")

    val f1 = getOrThrow(successResult)
    val f2 = getOrThrow(errorResult)
    val f3 = getOrThrow(errorResult2)

    Thread.sleep(100)

    println(f1)
    println(f2)
    println(f3)

    println("****************")

    val f4 = incr(f1)
    val f5 = incr(f2)
    val f6 = incr(f3)

    Thread.sleep(100)
    println(f4)
    println(f5)
    println(f6)

  }

  def getOrThrow(eF: Future[Either[String, Int]]): Future[Int] = eF.map {
    case Left(value)  => throw new RuntimeException(value)
    case Right(value) => value
  }

  def getOrThrow2(eF: Future[Either[String, Int]]): Future[Int] = eF.map { e =>
    e.fold(throw new RuntimeException, identity)
  }

  def incr(f: Future[Int]): Future[Int] = f.map(_ + 1)

  //map  -------> Functor -> Category
  //flatMap  -------> Monad -> Category
}
