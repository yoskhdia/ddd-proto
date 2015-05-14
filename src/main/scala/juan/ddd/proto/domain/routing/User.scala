package juan.ddd.proto.domain.routing

import juan.ddd.proto.domain.EntityBase
import org.sisioh.dddbase.core.model.Identifier

import scala.util._

case class UserId(value: String) extends Identifier[String]

trait User extends EntityBase[UserId] {

  val request: Option[Request]

  def askRouteTo(router: Router): Try[Route]

  def attach(request: Request): User
}

object User {
  def apply(identifier: UserId): User = {
    this.apply(identifier, null)
  }
  def apply(identifier: UserId, request: Request): User = {
    new UserImpl(identifier, Option(request))
  }
}

private[domain] case class UserImpl(identifier: UserId, request: Option[Request]) extends User {

  override def askRouteTo(router: Router): Try[Route] = {
    Try {
      val r = request match {
        case Some(x) => router.nextRoute(x)
        case None => None
      }
      r match {
        case Some(route) => route
        case None => throw new RuntimeException("does not contain request.")
      }
    }
  }

  override def attach(request: Request): User = {
    User.apply(identifier, request)
  }
}
