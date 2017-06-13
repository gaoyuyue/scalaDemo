/**
  * Created by arthurme on 2017/6/13.
  */
object DRY {
  def main(args: Array[String]): Unit = {
    val emailFilter:EmailFilter = notSentByAnyOf(Set("gaoyuyue@outlook.com"))
    val mails = Email(
      subject = "subject",
      text = "text",
      sender = "gaoyuyue@outlook.com",
      recipient = "gaoyuyue@outlook.com"
    ) :: Nil
    val user = newMailForUser(mails,emailFilter)
    println(user)
  }
  type EmailFilter = Email => Boolean
  def newMailForUser(mails:Seq[Email],f:EmailFilter) = mails.filter(f)

  val sentByOneOf:Set[String] => EmailFilter =
    senders =>
      email => senders.contains(email.sender)

//  val notSentByAnyOf: Set[String] => EmailFilter =
//    senders =>
//      email => !senders.contains(email.sender)

  def complement[A](predicate:A => Boolean) = (a:A) => !predicate(a)

  val notSentByAnyOf = sentByOneOf andThen(complement(_))

//  val minimumSize:Int => EmailFilter =
//    n =>
//      email => email.text.size >= n
//
//  val maximumSize: Int => EmailFilter =
//    n =>
//      email => email.text.size <= n

  type SizeChecker = Int => Boolean
  val sizeConstraint: SizeChecker => EmailFilter =
    f =>
      email => f(email.text.size)

  val minimumSize: Int => EmailFilter =
    n => sizeConstraint(_ >= n)

  val maximumSize: Int => EmailFilter =
    n => sizeConstraint(_ <= n)

  def any[A](predicates:(A => Boolean)*):A => Boolean =
    a => predicates.exists(pred => pred(a))

  def none[A](predicates:(A => Boolean)*): A => Boolean =
    complement(any(predicates:_*))

  def every[A](predicates:(A => Boolean)*): A => Boolean =
    none(predicates.view.map(complement(_)): _*)

  val filter: EmailFilter = every(
    notSentByAnyOf(Set("gaoyuyue@outlook.com")),
    minimumSize(100),
    maximumSize(1000)
  )

  val addMissingSubject = (email:Email) =>
    if(email.subject.isEmpty) email.copy(subject = "No subject")
    else email

  val checkSpelling = (email:Email) =>
    email.copy(text = email.text.replace("your","you are"))

  val pipline = Function.chain(Seq(
    addMissingSubject,
    checkSpelling
  ))

  val piplineAT = addMissingSubject andThen checkSpelling
}

case class Email(
                subject:String,
                text:String,
                sender:String,
                recipient:String
                )

