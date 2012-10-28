import scala.reflect.macros.Context
import language.experimental.macros
import language.implicitConversions

object MacrosDuke {
  def desugar(a: Any): String = macro desugarImpl

  def desugarImpl(c: Context)(a: c.Expr[Any]) = {
    import c.universe._

    c.Expr(Literal(Constant(show(a.tree))))
  }


  def log[A](a: A, raw:Boolean): A = macro logImpl[A]

  def logImpl[A: c.WeakTypeTag](c: Context)(a: c.Expr[A], raw:c.Expr[Boolean]): c.Expr[A] = {
    import c.universe._
    val aCode = c.Expr(Literal(Constant(show(a.tree))))
    val aRaw = c.Expr(Literal(Constant(showRaw(a.tree))))
    val Literal(Constant( isRaw:Boolean ) ) = raw.tree

    if ( isRaw ) c.universe.reify {
      println( aRaw.splice + " = " + a.splice )
      a.splice
    }
    else c.universe.reify {
      println( aCode.splice + " = " + a.splice )
      a.splice
    }
  }


  implicit def enrichStringContext(sc: StringContext) = new RichStringContext(sc)

  class RichStringContext(sc: StringContext) {
    // This is how a non-macro version would be implemented.
    // def b() = {
    //   val s = sc.parts.mkString
    //   parseBinary(s).getOrElse(sys.error("invalid binary literal: " + s))
    // }

    /** Binary literal integer
      *
      * {{{
      *  scala> b"101010"
      *  res0: Int = 42
      * }}}
      */
    def b(): Int = macro bImpl
  }

  def bImpl(c: Context)(): c.Expr[Int] = {
    import c.universe._

    def parseBinary(s: String): Int = {
      var i = s.length - 1
      var sum = 0
      var mult = 1
      while (i >= 0) {
        s.charAt(i) match {
          case '1' => sum += mult
          case '0' =>
          case x =>
            c.abort(c.enclosingPosition, "invalid binary literal")
        }
        mult *= 2
        i -= 1
      }
      sum
    }


    val i = c.prefix.tree match {
      // e.g: `c.g.r.m.Macrocosm.enrichStringContext(scala.StringContext.apply("1111"))`
      case Apply(_, List(Apply(_, List(Literal(Constant(const: String)))))) =>
        parseBinary(const)
      case x =>
        c.abort(c.enclosingPosition, "unexpected tree: " + show(x))
    }
    c.Expr[Int](Literal(Constant(i)))
  }


}