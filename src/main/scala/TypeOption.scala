/**
  * Created by arthurme on 2017/6/6.
  */
object TypeOption {
  def main(args: Array[String]): Unit = {
//    val greeting: Option[String] = Some("hello")
//    val nogreeting: Option[String] = None
//
//    val absentGreeting: Option[String] = Option(null)
//    val presentGreeting: Option[String] = Option("hello")
//
//    println(greeting)
//    println(nogreeting)
//    println(absentGreeting)
//    println(presentGreeting)

    //笨重方式
    val user1 = UserRepository.findById(1)
    if (user1.isDefined){
      println(user1.get.firstName)
    }

    //设置默认值 getOrElsefangfa
    val user2 = User(3,"jj","ll",33,None)
    println(user2.gender.getOrElse("not value"))

    //模式匹配
    val gender = user2.gender match {
      case Some(gender) => gender
      case None => "None"
    }
    println(gender)

    //集合Option
    user1.foreach(user => println(user.age))

    val age = user1.map(_.age)

    val g = user1.map(_.gender)    //返回Option[Option[String]]
    println(g)

    val gg = user1.flatMap(_.gender) //返回Option[String]
    println(gg)

    //flatMap函数作用 -> 扁平化
    val names: List[List[String]] = List(List("aa","bb","cc"),List(),List("pp"))
    val l1 = names.map(_.map(_.toUpperCase))
    val l2 = names.flatMap(_.map(_.toUpperCase))
    println(l1)
    println(l2)

    val maybeUser = user1.filter(_.age>6)
    //println(maybeUser.get)  //None.get报错
    println(maybeUser)

    for{
      user <- UserRepository.findById(1)
      gender <- user.gender
    } yield gender

    val genders = for{
      User(_,_,_,_,Some(gender)) <- UserRepository.findAll
    }yield gender
    println(genders)

    //链接Option
    val refcd: Option[Resource] = None
    val rfc: Option[Resource] = Some(Resource("jj"))
    val re = refcd orElse rfc
    println(re)
  }
}

case class User(
               id:Int,
               firstName:String,
               lastName:String,
               age:Int,
               gender:Option[String]
               )
object UserRepository{
  private val users = Map(
    1 -> User(1,"John","Doe",32,Some("haha")),
    2 -> User(2,"Make","Doe",30,Some("hihi"))
  )

  def findById(id:Int):Option[User] = users.get(id)
  def findAll = users.values
}

case class Resource(content:String)
