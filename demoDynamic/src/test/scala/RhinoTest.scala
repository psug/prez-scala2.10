

import org.junit.Test
import util.Try


class DynamicTest {


  @Test
  def ahoy() {
    RunScript.main(Array("Math.cos(Math.PI)"))

  }


  @Test
  def Plouf() {

    println(~JS.Math.PI)

    assert(~JS.Math.cos(JS.Math.PI) == Try("-1"))
    assert(~JS.Math.cos(0) == Try("1"))


    println(~JS.helloPSUG())
  }

}
