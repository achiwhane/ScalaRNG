sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coin: Int)


object Machine {
  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = ???


  def main(args: Array[String]): Unit = {
    // TODO: implement me
  }
}