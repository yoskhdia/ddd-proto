package juan.ddd.proto.app

import juan.ddd.proto.app.climate.{ClimateServiceImpl, ClimateService}
import juan.ddd.proto.app.climate.wbclimate.{WbClimateClientImpl, WbClimateClient}
import juan.ddd.proto.app.db.{DbServiceImpl, DbService}
import net.codingwell.scalaguice.ScalaModule

class ServicesModule extends ScalaModule {
  def configure {
    bind[ClimateService].to[ClimateServiceImpl]
    bind[WbClimateClient].to[WbClimateClientImpl]
    bind[DbService].to[DbServiceImpl]
  }
}
