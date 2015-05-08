package juan.ddd.proto.akka

import akka.actor.{Actor, IndirectActorProducer}
import com.google.inject.Injector

/**
 * Allows akka to instantiate guice injected instances
 */
class GuiceActorProducer[A <: Actor](injector: Injector, actorClz: Class[A])
  extends IndirectActorProducer
{
  override def actorClass = {
    actorClz
  }

  override def produce = {
    injector.getInstance(actorClass)
  }
}
