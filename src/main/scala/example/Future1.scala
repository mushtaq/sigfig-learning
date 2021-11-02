package example

import java.util.concurrent.{Executors, ScheduledExecutorService, ScheduledFuture, TimeUnit}
import scala.concurrent.{ExecutionContext, Future}

object Future1 {

  def main(args: Array[String]): Unit = {

    val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    val ec = ExecutionContext.fromExecutorService(scheduledExecutorService)

    def t1() = (1 to 100).foreach { x =>
      new Thread { () =>
        println(x -> Rbi.validate(x))
      }.start()
    }

//    def t2() = (1 to 100).foreach { x =>
//      new Thread { () =>
//        Rbi.asyncValidate(x, result => println(x -> result))(scheduledExecutorService)
//      }.start()
//    }

    import scala.concurrent.ExecutionContext.Implicits.global

//    (1 to 100).foreach { x =>
//      Future {
//        Rbi.asyncValidate(x, result => println(x -> result))(scheduledExecutorService)
//      }(ec)
//    }

    (1 to 100).foreach { x =>
      scheduledExecutorService.schedule(
        () => Rbi.asyncValidate(x, result => println(x -> result))(scheduledExecutorService),
        0,
        TimeUnit.MILLISECONDS
      )
    }

    t1()

    Thread.sleep(Int.MaxValue)
  }

}

object Rbi {
  def validate(accountId: Int): Boolean = {
    Thread.sleep(100)
    true
  }

  def asyncValidate(accountId: Int, f: Boolean => Unit)(scheduler: ScheduledExecutorService) = {
    val runnable: Runnable = () => f(true)
    scheduler.schedule(
      runnable,
      1000,
      TimeUnit.MILLISECONDS
    )
  }
}
