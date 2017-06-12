import java.io.{FileNotFoundException, InputStream}
import java.net.{MalformedURLException, URL}

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Created by arthurme on 2017/6/10.
  */
object TryCatch {
  type Citizen = String
  case class BlackListedResource(url:URL,visitors:Set[Citizen])
  def main(args: Array[String]): Unit = {
    try {
      buyCigarettes(Customer(10))
    }catch {
      case UnderAgeException(msg) => println(msg)
    }

    val url = parseURL("abc") getOrElse new URL("http://www.baidu.com")
    println(url)

    val a = parseURL("http://www.baidu.com").map(_.getProtocol)
    val b = parseURL("hello").map(_.getProtocol)
    println(a)
    println(b)

    val forURL = inputStreamForURL("http://www.baidu.com")
    println(forURL)

    val y = parseHttpURL("http://www.baidu.com")
    val z = parseHttpURL("ws://www.hello.com")
    val x = parseHttpURL("hello")
    println(x)
    println(y)
    println(z)
    y.foreach(println)

    val content = getURLContent("http://www.baidu.com")
    println(content)

    getURLContent("http://www.baidu.com") match {
      case Success(lines) => lines.foreach(println)
      case Failure(ex) => println(s"error ${ex.getMessage}")
    }

    val conten = getURLContent("hello") recover {
      case e:FileNotFoundException => Iterator("file not found")
      case e:MalformedURLException => Iterator("please make sure to enter a valid URL")
      case _ => Iterator("sorry")
    }
    conten.get.foreach(println)
    val contents = getContents(new URL("http://www.youdao.com"))
    println(contents.isLeft)

    contents match {
      case Left(msg) => println(msg)
      case Right(source) => source.getLines.foreach(println)
    }

    val maps: Either[String,Iterator[String]] = contents.right.map(_.getLines())
    println(maps)

    val count = averageLineCount(new URL("http://www.youdao.com"),new URL("http://www.youdao.com"))
    count.right.map(println)

    val toOption = count.right.toOption
    println(toOption)

    val toSeq = count.toSeq
    println(toSeq)

    val conee: Iterator[String] =
      getContents(new URL("http://www.youdao.com")).fold(Iterator(_),_.getLines())
    println(conee)

    val blaclList = List(
      BlackListedResource(new URL("http://www.baidu.con"),Set("hha","ll")),
      BlackListedResource(new URL("http://www.youdao.com"),Set("jk","kk"))
    )

    val checkedBlacklist: List[Either[URL,Set[Citizen]]] =
      blaclList.map(resource =>
        if(resource.visitors.isEmpty) Left(resource.url)
        else Right(resource.visitors)
      )

    val ss = checkedBlacklist.flatMap(_.left.toOption)
    val pc = checkedBlacklist.flatMap(_.right.toOption).flatten.toSet
    println(ss)
    println(pc)
  }

  def averageLineCount(url1:URL,url2:URL):Either[String,Int] =
    for{
      source1 <- getContents(url1).right
      source2 <- getContents(url2).right
      lines1 = source1.getLines().size
      lines2 = source2.getLines().size
    }yield (lines1+lines2)/2

  def getContents(url: URL): Either[String,Source] =         //Either无偏情况下不能看成集合
    if (url.getHost.contains("baidu"))
      Left("haha")
    else
      Right(Source.fromURL(url))

  def getURLContent(url:String):Try[Iterator[String]] =
    for{
      url <- parseURL(url)
      conn <- Try(url.openConnection())
      is <- Try(conn.getInputStream)
      source = Source.fromInputStream(is)
//      source = Source.fromURL(url)
    }yield source.getLines()

  def parseHttpURL(url:String) = parseURL(url).filter(_.getProtocol == "http")

  def inputStreamForURL(url:String):Try[InputStream] = parseURL(url) flatMap {
    u => Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
  }

  def parseURL(url:String):Try[URL] = Try(new URL(url))

  def buyCigarettes(customer: Customer):Cigarettes =
    if(customer.age<16)throw UnderAgeException(s"is ${customer.age}")
    else new Cigarettes
}

case class Customer(age: Int)
class Cigarettes
case class UnderAgeException(message:String) extends Exception(message)
