import org.junit.Test


class DynamicTest {

  @Test
  def ahoy() {

    val myDynamicStuff = new DynamicDuke
    assert(myDynamicStuff.ahoy == "ahoy Duke")
    assert(myDynamicStuff.plouf == "plouf Duke")
    assert(myDynamicStuff.ahoy("ça","va","?")== "ahoy Duke, ça va ?")

  }

}
