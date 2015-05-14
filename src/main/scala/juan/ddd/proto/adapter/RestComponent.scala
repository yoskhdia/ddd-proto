package juan.ddd.proto.adapter

import eu.bitwalker.useragentutils.{UserAgent => EuUserAgent, DeviceType => EuDeviceType}
import juan.ddd.proto.domain.routing._
import spray.routing.Route

trait RestComponent
{
  def route: Route

  def toRequest(request: spray.http.HttpRequest): Request = {
    val userAgent = request.headers.find(_.is("user-agent")).map(h => new EuUserAgent(h.value))
    userAgent match {
      case Some(agent) => {
        val browser = Browser(agent.getBrowser.name, agent.getBrowserVersion.getVersion)
        val deviceType = agent.getOperatingSystem.getDeviceType match {
          case EuDeviceType.COMPUTER => DeviceType.PC
          case EuDeviceType.MOBILE => DeviceType.Mobile
          case _ => DeviceType.Unknown
        }
        val requestUri = Uri(request.method.value, request.uri.path.toString())
        Request(UserAgentImpl(deviceType, browser, agent.toString()), RefererImpl(requestUri))
      }
      case None => Request.empty
    }
  }
}

case class UserAgentImpl(deviceType: DeviceType.DeviceType, browser: Browser, rawString: String) extends UserAgent

case class RefererImpl(uri: Uri) extends Referer
