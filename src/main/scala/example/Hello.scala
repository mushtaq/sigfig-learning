package example

import demo.Main

object Hello extends Greeting with App {
  println(Main.square(9))
}

trait Greeting {
  lazy val greeting: String = "hello"
}
