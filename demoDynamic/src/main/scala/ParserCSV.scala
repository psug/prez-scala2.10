import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import language.dynamics
import scalaz._
import scalaz.Scalaz._
import ValidationType.V

object ValidationType {
  type V[X] = ValidationNEL[String,X]
}

import TypeConverter._


object ParserCSV {

  class ValidationConversion(v:V[CSVValue]) {
    def to[T](implicit typeConv:TypeConv[T], man:Manifest[T]):V[T] = {
      v.flatMap(s => TypeConverter.typeconv[T](s.value))
    }

    def s = to[String]
  }

  implicit def toValidationConversion(v:V[CSVValue]) = new ValidationConversion(v)

  class CSVValue(val value:String) extends AnyVal

  def CSVValue(value:String) = new CSVValue(value)

  sealed class CSVLine(values:Array[String], headers:Option[Array[String]]) extends Dynamic {

    def selectDynamic(field: String): V[CSVValue] = {

      def getValueAtI(i:Int):V[String] = {
        if (i > values.length) {
          (s"index $i out of ligne bound (${values.length})").failureNel
        } else if(i < 1) {
          "index should be >= 1".failureNel
        } else {
          values(i-1).successNel
        }
      }

      (field.toCharArray.toList match {
        // processing access like ligne._1 , ligne._2
        case '_' :: tail => typeconv[Int](tail.mkString).bimap(_.map(v => "field index should take the form of _DD, found " +
          field), x => x).flatMap(getValueAtI)

        case _ => headers match {
          case Some(h) => h.zipWithIndex.find(_._1 == field) match {
            case Some((_, i)) => getValueAtI(i+1)
            case None =>  s"header $field not found".failureNel
          }
          case _ => "header not found".failureNel
        }

      }) map(CSVValue)
    }
  }


  case class CSVParser(filename:String, withHeader:Boolean) {
    def apply[T](f:CSVLine => T):Iterator[T] = {
      val reader = new CSVReader(new FileReader(filename ))

      val header = withHeader match {
        case true => Option(reader.readNext())
        case _ => None
      }

      Iterator.continually(reader.readNext()).takeWhile(_ != null).map(v => f(new CSVLine(v,header)))
    }
  }

  def readCSV[T](filename:String, withHeader:Boolean = true) = CSVParser(filename,withHeader)
}





