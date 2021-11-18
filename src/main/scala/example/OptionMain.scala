package example

object OptionMain {
  def main(args: Array[String]): Unit = {
    val opt: Option[Int]  = Some(1)
    val opt2: Option[Int] = None

    val opt3: Option[Int] = Option(1)    // List(1)
    val opt4: Option[Int] = Option.empty // List.empty

    println(opt)
    println(opt2)
    println(opt3)
    println(opt4)

    def incr(option: Option[Int]): Option[Int] = option match {
      case Some(value) => Some(value + 1)
      case None        => None
    }

    def incrA(option: Option[Int]): Option[Int] = option.map(_ + 1)

    def incr2(list: List[Int]): List[Int] = list match {
      case List(value) => List(value + 1)
      case Nil         => Nil
    }

    def incr2b(list: List[Int]): List[Int] = list.map(_ + 1)

  }
}
