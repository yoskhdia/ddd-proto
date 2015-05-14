package juan.ddd.proto.domain.routing

object DeviceType extends Enumeration {
  type DeviceType = Value
  val Mobile, PC, Unknown = Value
}

case class Browser(name: String, version: String)
object Browser {
  def empty = Browser("", "")
}

trait UserAgent {

  import DeviceType._

  val deviceType: DeviceType

  val browser: Browser

  val rawString: String
}
