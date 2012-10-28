import org.junit.Test


class MacrosTest {
  import MacrosDuke._

  @Test
  def desugarTest() {
    println( desugar(1+"duke" ) )
    println( desugar( "plouf".toUpperCase ) )
    println(desugar((1 to 10).map(_ + 1)))

    println(desugar((_:String) => {
      println("ahoy")
      "duke"
    }))
  }

  @Test
  def logIsAllMyLife() {
    log( 1 + 2, true )

    log(log("plouf".toUpperCase, false).contains("P"), false )
  }

  @Test
  def binaryDude() {

    println("010011 binary to decimal: " + b"010011")
  }

  }