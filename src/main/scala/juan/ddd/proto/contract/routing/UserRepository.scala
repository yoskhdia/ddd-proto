package juan.ddd.proto.contract.routing

import juan.ddd.proto.domain.routing._

trait UserRepository {

  def nextIdentity: UserId

  def userOfId(id: UserId): Option[User]

  def remove(user: User): Unit

  def save(user: User): Unit
}
