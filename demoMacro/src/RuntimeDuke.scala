
import language.experimental.macros
import scala.reflect.runtime.{universe=>ru}
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

object RuntimeDuke {
  val toolbox = ru.runtimeMirror(getClass.getClassLoader).mkToolBox()
  import toolbox._

  def astPlus( x:Int, y:Int ) = {

    val tree = parse( s"($x + $y).toString")
    eval(tree)

  }

  def matchList[T]( list:List[T] ) = list match {
    case _: List[String] => "strings"
    case _: List[Int] => "ints"
    case _ => "unknown"
  }

  def matchListReflect[T]( list:List[T] )(implicit tt: TypeTag[T]) = tt.tpe match {
    case t if t =:= typeOf[String] => "strings"
    case t if t =:= typeOf[Int] => "ints"
    case _ => "unknown"
  }


  def introspect[T]( implicit tt:TypeTag[T] ) = {
    typeOf[T].declarations.filter(_.isMethod).map(_.name.toString)
  }
}
