package juan.ddd.proto.domain.routing

case class Request(userAgent: UserAgent, referer: Referer)

object Request {
  def empty = null
}
