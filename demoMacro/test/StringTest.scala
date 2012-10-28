import org.junit.Test

class StringTest {

  import StringDuke._
  @Test
  def helloTest {
    val s = hello( Personne( "Duke" ) )
    assert( s == "Ahoy DUKE !" )
  }
}
