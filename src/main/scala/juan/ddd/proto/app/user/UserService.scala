package juan.ddd.proto.app.user

import javax.inject.Inject

import juan.ddd.proto.app.db.DbService

/**
 */
trait UserService {
  def authenticate(name: String, password: String)
}

class UserServiceImpl @Inject()(
  db: DbService
) extends UserService
{
  def create(name: String, password: String) = {

  }

  def authenticate(name: String, password: String) = {

  }

}

