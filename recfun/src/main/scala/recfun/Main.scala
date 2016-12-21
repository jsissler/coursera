package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
    println("Balanced Parens")
    println("balanced? ()" + balance(List('(', ')')))
    println("balanced? )(" + balance(List(')', '(')))
    println("count 1: " + countChange(4, List(1)))
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    // Check inputs
    if (c < 0 || r < 0 || c > r)
      throw new IllegalArgumentException("illegal args to pascal function")
    // Optimize boundary cases
    if (c == 0 || c == r || r < 2)
      1
    else {
      def genpascal(i: Int, l: List[Int]): List[Int] = {
        if (i > r)
          l
        else {
          var irow = new Array[Int](i + 1)
          irow(0) = 1
          irow(i) = 1
          for (j <- 0 until l.length - 1)
            irow(j + 1) = l(j) + l(j + 1)
          genpascal(i + 1, irow.toList)
        }
      }
      genpascal(2, List[Int](1, 1))(c)
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def walkChars(chars: List[Char], counter: Int): Boolean = {
      if (chars.isEmpty)
        counter == 0
      else if (counter < 0)
        false
      else {
        if (chars.head == '(')
          walkChars(chars.tail, counter + 1)
        else if (chars.head == ')')
          walkChars(chars.tail, counter - 1)
        else
          walkChars(chars.tail, counter)
      }
    }
    walkChars(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (money > 0 && !coins.isEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else
      0
  }
}
