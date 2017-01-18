
object play {
  def l = List(('a', 10), ('c', 2), ('e', 3))     //> l: => List[(Char, Int)]
  l partition (x => x._2 < 10)                    //> res0: (List[(Char, Int)], List[(Char, Int)]) = (List((c,2), (e,3)),List((a,10
                                                  //| )))
  def l2 = List('b', 'x', 'h', 'h', 'c')          //> l2: => List[Char]
  def p1 = l2 partition (x => x == 'h')           //> p1: => (List[Char], List[Char])
  p1                                              //> res1: (List[Char], List[Char]) = (List(h, h),List(b, x, c))
  p1._1                                           //> res2: List[Char] = List(h, h)
  p1._1.size                                      //> res3: Int = 2
}

/*
object play {
  println("Welcome to the Scala worksheet")
  def l = List(('a', 10), ('c', 2), ('e', 3))
  l.head
  val s = l.sortBy(x => x._2)
  l.head
  l.tail
  def p = ('a', 1)
  p
  p._1
  def p1 = l partition (x => x._1 == 'a')
  def p2 = l partition (x => x._1 == 'b')
  def empt = p2._1
  empt.isEmpty
  p1
}
*/