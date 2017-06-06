package pattern

/**
  * Created by arthurme on 2017/6/6.
  */
object PatternAndFunction {
  def main(args: Array[String]): Unit = {
    val songTitles = "The White Hare" :: "Childe The Hunter" :: "thinks" :: Nil
//    songTitles.map(t => t.toLowerCase)    //匿名函数
    val lower = songTitles.map(_.toLowerCase)           //占位符语法
    println(lower)

    val wordFrequencies = ("hello",6) :: ("hi",1) :: ("haha",7) :: Nil
    val outliers = wordsWithoutOutliers(wordFrequencies)
    println(outliers)

    println(wordsWithoutOutliersByPattern(wordFrequencies))            //匿名函数里的模式匹配

    val predicate: (String,Int) => Boolean = {                         //将匿名函数赋给变量 （必须显示的声明值的类型）
      case (_,f) => f>3 && f<25
    }

    val transFormFn: (String,Int) => String = {
      case (w,_) => w
    }

    println(wordsWithoutOutliersByCollect(wordFrequencies))
  }

  def wordsWithoutOutliers(wordFrequencies:Seq[(String,Int)]):Seq[String] = wordFrequencies.filter(wf => wf._2>3 && wf._2<25).map(_._1)

  def wordsWithoutOutliersByPattern(wordFrequencies:Seq[(String,Int)]):Seq[String] = wordFrequencies filter {case (_,f) => f>3 && f<25} map {case (w,_) => w}

  /**
    * 偏函数实现，collect接受偏函数作为参数，完成filter,map功能
    * @param wordFrequencies
    * @return
    */
  def wordsWithoutOutliersByCollect(wordFrequencies:Seq[(String,Int)]):Seq[String] = wordFrequencies collect {
    case (word, freq) if freq>3 && freq<25 => word
  }
}
