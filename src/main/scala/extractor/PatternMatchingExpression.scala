package extractor

/**
  * Created by arthurme on 2017/6/5.
  */
object PatternMatchingExpression {
  def play(player: Player) = player match {
    case Player(_,score) if score > 10 => "hello"
    case Player(name,_) => name
  }

  def main(args: Array[String]): Unit = {
    val player = Player("hi",22)
    println(play(player))

    val Player(name,_) = currentPlayer()      //值定义中的模式:赋给左侧变量的同时解构
    println(name)

//    val head :: last = scores()         //确保模式总能够匹配
//    println(head)

    val (a,b) = gameResult()               //使用元组
    println(a+b)

    println(hallOfFame)                   // for中的模式

    val lists = List(1,2,3) :: List.empty :: List(5,6) :: Nil
    val r = for {
      list @ head :: _ <- lists            //左侧模式过滤不符合条件的
    }yield list.length

    println(r)
  }

  def currentPlayer():Player = Player("haha",1000)

  def gameResult():(String,Double) = ("haha",111)

  def scores():List[Int] = List()

  def myResult():Seq[(String,Int)] = ("haha",1) :: ("hihi",2) :: ("gege",3) :: Nil

  def hallOfFame = for {
    (name,score) <- myResult()
    if score > 1
  } yield name
}

case class Player(name:String,score:Double)

