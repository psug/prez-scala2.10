
import org.junit.Test


class RuntimeTest {

  import RuntimeDuke._

  class TestClass {
    def dummy(s:String):Int = s.toInt
  }


  @Test
  def introspectTest {
    assert( introspect[TestClass].exists( _ == "dummy") )
  }

  @Test
  def matchListTest {
    assert( matchList( List("1") ) == "strings" )
    assert( matchList( List(1) ) != "ints" )

    assert( matchListReflect( List("1") ) == "strings" )
    assert( matchListReflect( List(1) ) == "ints" )

  }

  @Test
  def runtimeTest {
    assert( astPlus( 3, 4 ) == "7" )
  }



}
