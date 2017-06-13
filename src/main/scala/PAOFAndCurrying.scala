import com.sun.crypto.provider.AESCipher.AES128_CBC_NoPadding

/**
  * Created by arthurme on 2017/6/13.
  */
object PAOFAndCurrying {
  type IntPairPred = (Int,Int) => Boolean
  def sizeConstraint(pred:IntPairPred,n:Int,email: Email)=
    pred(email.text.size,n)
  val gt:IntPairPred = _>_
  val ge:IntPairPred = _>=_
  val lt:IntPairPred = _<_
  val le:IntPairPred = _<=_
  val eq:IntPairPred = _==_
  val minimumSize:(Int,Email) => Boolean =
    sizeConstraint(ge,_:Int,_:Email)

  val sizeConstrainFn:(IntPairPred,Int,Email) => Boolean = sizeConstraint _

  def sizeConstraintC(intPairPred: IntPairPred)(n:Int)(email: Email):Boolean =
    intPairPred(email.text.size,n)

  val sizeConstrainFnC:IntPairPred=>Int=>Email=>Boolean = sizeConstraintC _

  val minimumSizeC: Int=>Email=>Boolean = sizeConstrainFnC(ge)
  val min20:Email => Boolean = minimumSizeC(20)

  val sum:(Int,Int) => Int = _+_
  val sumCurry:Int=>Int=>Int = sum.curried
}
case class Email(
                subject:String,
                text:String,
                sender:String,
                recipient:String
                )
type EmailFilter = Email => Boolean
