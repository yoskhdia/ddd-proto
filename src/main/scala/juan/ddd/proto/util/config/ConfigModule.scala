package juan.ddd.proto.util.config

import com.typesafe.config.ConfigValueType._
import com.typesafe.config.{Config, ConfigFactory, ConfigRenderOptions, ConfigValue}
import com.typesafe.scalalogging.slf4j.LazyLogging
import net.codingwell.scalaguice.BindingExtensions._
import net.codingwell.scalaguice.ScalaModule

import scala.collection.JavaConversions._

/**
 * This module binds each property defined in the active typesafe config to
 * a ''@Named("<key>"'' annotation.  This makes any config property value
 * accessible via injection by annotating a constructor parameter with the desired
 * ''@Named("<key>")'' annotation.
 *
 * For example
 * {{{
 * class ExampleService @Inject()(@Named("example.timeout") timeout: Int) {
 *   ...
 * }
 * }}}
 * can be declared to inject timeout with the value obtained by calling
 * Config.getInt("example.timeout").
 *
 * Also binds the active configuration to the Config type so that injectable classes
 * can obtain the config object directly.
 *
 * For example
 * {{{
 * class ExampleService @Inject()(config: Config)) {
 *   timeout = config.get("example.timeout")
 *   ...
 * }
 * }}}
 * can be declared to inject config with the active Config object.
 *
 * The module also calls [[ConfigFactoryExt.enableEnvOverride]] to enable environment
 * specific configuration use the 'env' system property.
 */
class ConfigModule extends ScalaModule with LazyLogging
{
  def configure {
    val config = loadConfig
    bind[Config].toInstance(config)
    bindConfig(config)
  }

  protected[this] def loadConfig() = {
    ConfigFactoryExt.enableEnvOverride
    val config = ConfigFactory.load
    logger.info("config loaded.")
    logger.trace(s"${config.root.render(ConfigRenderOptions.concise.setFormatted(true))}")
    config
  }

  /**
   * Binds every entry in Config to a @Named annotation using the fully qualified
   * config key as the name.
   */
  private def bindConfig(config: Config) {
    for (entry <- config.entrySet) {
      val cv = entry.getValue
      cv.valueType match {
        case STRING | NUMBER | BOOLEAN =>
          bindPrimitive(entry.getKey, entry.getValue)
        case LIST =>
          bindList(entry.getKey, entry.getValue)
        case NULL =>
          throw new AssertionError(
            s"Did not expect NULL entry in ConfigValue.entrySet: ${cv.origin}"
          )
        case OBJECT =>
          throw new AssertionError(
            s"Did not expect OBJECT entry in ConfigValue.entrySet: ${cv.origin}"
          )
      }
    }
  }

  /**
   * Bind a primitive value (Int, Double, Boolean, String) to a
   * '@Named("<key>") annotation.
   */
  private def bindPrimitive(key: String, value: ConfigValue) {
    val unwrapped = value.unwrapped.toString
    binderAccess.bindConstant.annotatedWithName(key).to(unwrapped)
  }

  /**
   * Bind a list value to '@Named("<key>") annotation.  This list contain any
   * primitive type.
   */
  private def bindList(key: String, value: ConfigValue) {
    val list = value.unwrapped.asInstanceOf[java.util.List[Any]]
    if (list.size == 0) {
      // Seq[Int|Double|Boolean] type params will only match a value bound as Seq[Any]
      bind[Seq[Any]].annotatedWithName(key).toInstance(Seq())
      // Seq[String] type params will only match a value bound as Seq[String]
      bind[Seq[String]].annotatedWithName(key).toInstance(Seq())
    } else {
      val seq = list.get(0) match {
        case x: Integer =>
          val v = list.collect({case x: java.lang.Integer => x.intValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: Double =>
          val v = list.collect({case x: java.lang.Double => x.doubleValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: Boolean =>
          val v = list.collect({case x: java.lang.Boolean => x.booleanValue}).toSeq
          bind[Seq[Any]].annotatedWithName(key).toInstance(v)
        case x: String =>
          val v = list.collect({case x: String => x}).toSeq
          bind[Seq[String]].annotatedWithName(key).toInstance(v)
        case x =>
          throw new AssertionError("Unsupported list type " + x.getClass)
      }
    }
  }
}

