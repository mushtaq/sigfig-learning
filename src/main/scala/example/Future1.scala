package example

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.concurrent.{ExecutionContext, Future, Promise}

object Future1 {

  def main(args: Array[String]): Unit = {

    val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    val scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor()
    implicit val ec2 = ExecutionContext.fromExecutorService(scheduledExecutorService2)

    val scheduledExecutorService3 = Executors.newSingleThreadScheduledExecutor()
    val ec3 = ExecutionContext.fromExecutorService(scheduledExecutorService3)

    //    (1 to 100).foreach { x =>
    //      scheduledExecutorService.schedule(
    //        () => Rbi.asyncValidate(x, result => println(x -> result))(scheduledExecutorService),
    //        0,
    //        TimeUnit.MILLISECONDS
    //      )
    //    }

    //    (1 to 100).foreach { x =>
    //      val f: Boolean => Unit = {
    //        Thread.sleep(1000)
    //        result => println(x -> result)
    //      }
    //      Rbi.asyncValidate(x, f)(scheduledExecutorService)
    //    }

    //    (1 to 100).foreach { x =>
    //      val future = Rbi.asyncValidate2(x)(scheduledExecutorService)
    //      future.foreach { result =>
    //        Thread.sleep(1000)
    //        println(x -> result -> "*********")
    //      }(ec3)
    //    }

    //    (1 to 100).foreach { x =>
    //      val future = Rbi.asyncValidate2(x)(scheduledExecutorService)
    //      future.foreach(result => println(x -> result))(ec2)
    //    }

    val future: Future[Int] = Rbi.getPrice(10)(scheduledExecutorService)
    val future2: Future[Int] = Rbi.getPrice(12)(scheduledExecutorService)

    val future3: Future[(Int, Int)] = future.zip(future2)

    val future4: Future[Int] = future3.map { case (x1, x2) => x1 + x2 }(ec2)

    future4.foreach(result => println(result))(ec2)


    val value: Seq[Future[Int]] = (1 to 100).map(x => Rbi.getPrice(x)(scheduledExecutorService))

    val future5: Future[Seq[Int]] = Future.sequence(value)

    val future6: Future[Int] = future5.map(xs => xs.sum)

    future6.foreach(result => println(result))

    Thread.sleep(Int.MaxValue)
  }

}

object Rbi {
  def getPrice(accountId: Int)(scheduler: ScheduledExecutorService): Future[Int] = {
    val p: Promise[Int] = Promise()
    scheduler.schedule(
      () => p.success(accountId),
      1000,
      TimeUnit.MILLISECONDS
    )
    p.future
  }

  def asyncValidate(accountId: Int, f: Boolean => Unit)(scheduler: ScheduledExecutorService) = {
    val runnable: Runnable = () => f(true)
    scheduler.schedule(
      runnable,
      1000,
      TimeUnit.MILLISECONDS
    )
  }

  def validate(accountId: Int): Boolean = {
    Thread.sleep(100)
    true
  }
}
