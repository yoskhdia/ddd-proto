package juan.ddd.proto.adapter.routing

import java.util.UUID

import juan.ddd.proto.contract.routing.UserRepository
import juan.ddd.proto.domain.routing.{User, UserId}

// In memory store.
class UserRepositoryImpl extends UserRepository {

  protected[this] val store = scala.collection.mutable.HashMap.empty[UserId, User]

  override def nextIdentity: UserId = {
    UserId(UUID.randomUUID().toString)
  }

  override def remove(user: User): Unit = {
    store.remove(user.identifier)
  }

  override def save(user: User): Unit = {
    store.put(user.identifier, user)
  }

  override def userOfId(id: UserId): Option[User] = {
    store.get(id)
  }
}
