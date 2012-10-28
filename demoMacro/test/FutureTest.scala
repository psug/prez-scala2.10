import org.junit.Test


/**
 * User: alag
 */

class FutureTest {

  @Test
  def promiseTest() {
   assert( FutureDuke.dukePromise == FutureDuke.duck )
  }

  @Test
  def flatMapTest() {
    assert( FutureDuke.dukeFuturesFlatMap( 3, 5, 7 ) == 3 + 5 + 7 )
  }

}
