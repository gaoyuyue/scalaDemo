package extractor

/**
  * Created by arthurme on 2017/6/5.
  */
object SeqExtractor {
  def main(args: Array[String]): Unit = {
    val xs = 10 :: 2 :: 3 :: Nil
    val result = xs match {
      case List(a,b) => a*b
      case List(a,b,c) => a*b*c
      case _ => 0
    }

    println(result)

    val xxs = 11 :: 12 :: 13 :: 14 :: Nil
    val xresult = xxs match {
      case List(a,b,_*) => a*b                  // `_*`匹配长度不确定的参数
      case _ => 0
    }

    println(xresult)

    val name = "Gao Yuyue"
    val nameResult = name match {
      case myName @ GetNames(firstNme,_*) => myName+" the firstName is "+firstNme
      case _ => "none"
    }

    println(nameResult)


    val formatName = "Yuyue attacke azz Gao"
    val resultFormat = formatName match {
      case GetNamesByFormat(firstName,lastName,_*) => firstName+lastName
      case _ => "none"
    }

    println(resultFormat)
  }
}

/**
  * 可变参数提取器
  */
object GetNames{
  def unapplySeq(name : String): Option[Seq[String]] ={
    val names = name.trim.split(" ")
    if(names.forall(_.isEmpty)) None
    else Some(names)
  }
}

object GetNamesByFormat{
  def unapplySeq(name:String):Option[(String,String,Seq[String])] = {   //返回三元组
    val names = name.trim.split(" ")
    if (names.length < 2) None
    else Some((names.last,names.head,names.drop(1).dropRight(1)))
  }
}
