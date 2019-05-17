package config

import ascii.{ImageRepository, InMemoryImageRepository}
import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}

class InMemoryModule(environment: Environment, configuration: Configuration) extends AbstractModule {

  override def configure() = {
    bind(classOf[ImageRepository]).to(classOf[InMemoryImageRepository])
  }
}
