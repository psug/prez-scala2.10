/**
 * User: alag
 * Date: 21/10/12
 */

import scala.concurrent._

import duration._

import ExecutionContext.Implicits.global

import scala.language.postfixOps

/*
 import ExecutionContext.Implicits.global above imports the default global execution context.
 Execution contexts execute tasks submitted to them, and you can think of execution contexts as thread pools.
 They are essential for the future method because they handle how and when the asynchronous computation is executed.
 You can define your own execution contexts and use them with future, but for now it is sufficient to know that you can import
 the default execution context as shown above.
*/

object FutureDuke {

  val duck = "Duck!"

  def dukePromise = {

    val p = promise[String]

    future {
      println("Cooking duke")
      Thread.sleep(1000)
      println("Dinner ready")
      p success (duck)
    }
    println("Where is my mind")


    Await.result(p.future, Duration(2,SECONDS))
  }


  def dukeFuturesFlatMap( i1:Int, i2:Int, i3:Int ) = {

    val f1 = future { i1 }
    val f2 = future { i2 }
    val f3 = future { i3 }


    val f4 = for( r1 <- f1;
                  r2 <- f2;
                  r3 <- f3 ) yield r1+r2+r3

    Await.result(f4, 2 seconds)


  }
}
