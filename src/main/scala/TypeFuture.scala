import java.util.concurrent.{Executor, Executors}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by arthurme on 2017/6/12.
  */
object TypeFuture {
  def main(args: Array[String]): Unit = {
//    prepareCappuccino()
//    grind("arabica beans").onComplete{
//      case Success(groundCoffee) => println(s"got my $groundCoffee")
//      case Failure(ex) => println("this grinder needs a replacement,seriously")
//    }
//
//    val tempreatureOkay:Future[Boolean] = heatWater(Water(25)) map {
//      water =>
//        println("we are in the future!")
//        (80 to 85) contains(water.temperature)
//    }
//
//    val nestedFuture:Future[Future[Boolean]] = heatWater(Water(25)) map {
//      water => temperatureOkay(water)
//    }
//
//    val flatFuture:Future[Boolean] = heatWater(Water(25)) flatMap {
//      water => temperatureOkay(water)
//    }
//
//    val acceptable:Future[Boolean] = for{
//      heatedWater <- heatWater(Water(25))
//      okay <- temperatureOkay(heatedWater)
//    } yield okay

//    val taxcut = Promise[TaxCut]()
//    Future{
//
//    }
//    val taxcutF:Future[TaxCut] = taxcut.future
//    println(taxcutF)
//    taxcutF.onComplete {
//      case Success(taxCut) => println(taxCut.reduction)
//      case Failure(ex) => println(-1)
//    }
//    taxcut.success(TaxCut(3))
    val taxCutF: Future[TaxCut] = recc()
    println(taxCutF)
    taxCutF onComplete {
      case Success(TaxCut(reduction)) => s"ll$reduction"
      case Failure(ex) => "no"
    }
  }

  def recc():Future[TaxCut] = {
    val p = Promise[TaxCut]()
    Future{
      println("starting")
      Thread.sleep(2000)
      p.success(TaxCut(2))
      println("we got")
    }
    p.future
//    val pool = Executors.newFixedThreadPool(4)
//    val executionContext = ExecutionContext.fromExecutorService(pool)

  }

  case class TaxCut(reduction:Int)

  //no
//  def prepareCappuccinoSequentially():Future[Cappuccino] = for {
//    ground <- grind("arabica beans")
//    water <- heatWater(Water(25))
//    foam <- frothMilk("milk")
//    espresso <- brew(ground,water)
//  } yield combine(espresso,foam)

    def prepareCappuccinoSequentially():Future[Cappuccino] = {
      val grindd = grind("arabica beans")
      val heatWaterd = heatWater(Water(25))
      val frothMilkd = frothMilk("milk")
      for {
        ground <- grindd
        water <- heatWaterd
        foam <- frothMilkd
        espresso <- brew(ground,water)
      } yield combine(espresso,foam)
    }

  type CoffeeBeams = String
  type GroundCoffee = String
  case class Water(temperature:Int)
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

//  def grind(beams: CoffeeBeams):GroundCoffee = s"ground coffee of $beams"
//  def heatWater(water: Water): Water = water.copy(temperature = 85)
//  def frothMilk(milk: Milk): FrothedMilk = s"frothed $milk"
//  def brew(coffee: GroundCoffee,heatedWater:Water): Espresso = "espresso"
  def combine(espresso: Espresso,frothedMilk: FrothedMilk): Cappuccino="cappuccino"

  def temperatureOkay(water: Water):Future[Boolean] = Future{
    (80 to 85) contains(water.temperature)
  }

  def grind(beans: CoffeeBeams): Future[GroundCoffee] = Future{
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if(beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future{
    println("heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("hot,it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk):Future[FrothedMilk] = Future{
    println("milk frothing system engaged!")
    Thread.sleep(Random.nextInt(2000))
    println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee:GroundCoffee,heatedWater:Water):Future[Espresso] = Future{
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    println("it's brewed!")
    "espresso"
  }

  case class GrindingException(msg:String) extends Exception(msg)
  case class FrothingException(msg:String) extends Exception(msg)
  case class WaterBoilngException(msg:String) extends Exception(msg)
  case class BrewingException(msg:String) extends Exception(msg)

//  def prepareCappuccino():Try[Cappuccino] = for {
//    ground <- Try(grind("arabica beans"))
//    water <- Try(heatWater(Water(25)))
//    espresso <- Try(brew(ground,water))
//    foam <- Try(frothMilk("milk"))
//  } yield combine(espresso,foam)


}
