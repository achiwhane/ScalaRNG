sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coin: Int)


object Machine {
  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = {
    def onCoin(currentState: Machine): Machine = currentState match {
      case Machine(locked, candies, coin) =>
        if (!locked) Machine(locked, candies, coin)
        else Machine(if (locked && candies > 0) false else locked, candies, coin + 1)
    }

    def onTurn(currentState: Machine): Machine = currentState match {
      case Machine(locked, candies, coin) =>
        if (locked) Machine(locked, candies, coin)
        else Machine(locked = true, candies - 1, coin)
    }

    def helper(inputs: List[Input], machine: Machine): Machine = inputs match {
      case Nil => machine
      case currentInput :: rest => currentInput match {
        case Coin => helper(rest, onCoin(machine))
        case Turn => helper(rest, onTurn(machine))
      }
    }

    State(s => {
      val res = helper(inputs, s)
      ((res.candies, res.coin), res)
    })
  }


  def main(args: Array[String]): Unit = {
    val simulator = simulateMachine(List[Input](Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn))

    println(simulator.run(Machine(locked = true, 5, 10)))
  }
}