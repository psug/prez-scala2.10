import java.io.FileReader
import org.mozilla.javascript.Context

import language.dynamics
import util.Try

object JS extends Dynamic {

  case class JSContext(context:String) extends Dynamic {
    def selectDynamic(method:String)= JSContext(context + "."+ method)

    def ^^ : Try[String] = Try {
      val cx: Context = Context.enter

      val scope = cx.initStandardObjects()

      cx.evaluateReader(scope, new FileReader("./hello.js"), "<cmd>", 1, null)
      val result = cx.evaluateString(scope, context, "<cmd>", 1, null)
      Context.toString(result)
    }

    def unary_~ = ^^
    def applyDynamic(method:String) = JSApplier(context + "." + method)

  }

  case class JSApplier(context:String) {
    def apply(js: JSContext) = JSContext(context + "(" + js.context + ")")
    def apply[T:Numeric](a:T) = JSContext(context+ "(" + a.toString + ")")

    def apply() = JSContext(context + "()")

  }
  def selectDynamic(method:String) = JSContext(method)

  def applyDynamic(method:String) = JSApplier(method)
}
