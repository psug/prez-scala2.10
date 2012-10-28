import TypeConverter.TypeConv
import ValidationType._
import scalaz._
import scalaz.Scalaz._

object TypeConverter {

  def typeconv[T](s:String)(implicit typeconv:TypeConv[T]):V[T] = typeconv.avoidNullConv(s)

  trait TypeConv[T] {
    def conv(s:String): V[T]
    def avoidNullConv(s:String): V[T] = if(s.isEmpty) {"Field is empty".failureNel} else {conv(s)}
  }

  implicit val intConv = new TypeConv[Int] {
    def conv(s: String) = {
      try {
        java.lang.Integer.parseInt(s).successNel
      } catch {
        case _:Exception => (s + " is not a number").failureNel
      }
    }
  }

  implicit val strConv = new TypeConv[String] {
    def conv(s: String) =  s.successNel
  }

  implicit def optionConv[T:TypeConv] = new TypeConv[Option[T]] {
    override def avoidNullConv(s:String) = conv(s)

    def conv(s:String) = s match {
      case "" => None.success
      case s  => typeconv[T](s).map(Option(_))
    }
  }


}

