package controllers

import net.repl.LispRepl
import net.parser.AST.{M, MValue}
import net.common.Error.LispError
import play.api._
import play.api.mvc._

object LispRunner extends Controller {

  def evaluate(currentExprs: String, previousExprs: String) = Action {
  	val previousWithCurrent = previousExprs + currentExprs
  	val result: Either[LispError, List[Either[(LispError, M), (MValue, M)]]] = 
  		LispRepl.evalString(previousWithCurrent)
  	result match {
  		case Left(parseErr) => Ok(views.html.lisp(List(parseErr.toString))(previousWithCurrent))
  		case Right(evald)   => Ok(views.html.lisp(evald.map(_.toString))(previousWithCurrent))
  	}
  }

}