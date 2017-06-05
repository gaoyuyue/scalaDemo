package extractor

/**
  * Created by arthurme on 2017/6/5.
  *
  * bool提取器
  */

object URLExtractor {
  def main(args: Array[String]): Unit = {
    val url = "http://www.baidu.com"
    val result = url match {
      case myUrl @ URLExtractor() => myUrl + " is url"
      case _ => "is not url"
    }

    println(result)
  }
  def unapply(arg: String): Boolean = arg.matches("[a-zA-z]+://[^\\s]*")
}



