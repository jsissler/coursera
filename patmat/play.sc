
object play {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  def l = List(('a', 1), ('c', 1))                //> l: => List[(Char, Int)]
  l.head                                          //> res0: (Char, Int) = (a,1)
  l.tail                                          //> res1: List[(Char, Int)] = List((c,1))
  def p = ('a', 1)                                //> p: => (Char, Int)
  p                                               //> res2: (Char, Int) = (a,1)
  p._1                                            //> res3: Char = a
  def p1 = l partition (x => x._1 == 'a')         //> p1: => (List[(Char, Int)], List[(Char, Int)])
  def p2 = l partition (x => x._1 == 'b')         //> p2: => (List[(Char, Int)], List[(Char, Int)])
  def empt = p2._1                                //> empt: => List[(Char, Int)]
  empt.isEmpty                                    //> res4: Boolean = true
  p1                                              //> res5: (List[(Char, Int)], List[(Char, Int)]) = (List((a,1)),List((c,1)))
}