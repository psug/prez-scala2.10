import language.dynamics
import scala.Dynamic



class DynamicDuke extends Dynamic {

  case class DynamicDuke(start:String) {
    def apply(args: Any*):String =  {
      start + ", " +  args.toList.map(_.toString).mkString(" ")
    }
  }
  
  def applyDynamic(method:String) : DynamicDuke = DynamicDuke(method + " Duke")

  def selectDynamic(method:String): String= {
     method + " Duke"
  }

}


class MyGenericDynamic extends Dynamic {

  case class _GenericDynamic(prefix:String) extends Dynamic{

    def selectDynamic(method:String) = _GenericDynamic(prefix+" "+method)

    def applyDynamic(method:String) = _GenericDynamic(prefix+" "+method)

    def apply(s:String) = _GenericDynamic(prefix+s)

    override def toString() = prefix
  }

  def selectDynamic(method:String) =  _GenericDynamic(method)

  def applyDynamic(method:String) = _GenericDynamic(method)
}

