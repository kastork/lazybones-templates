package ${_appPackage};

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.shell.command.CommandResolver;
import io.vertx.ext.web.Router;

import java.util.ServiceLoader;

import static io.vertx.core.Vertx.vertx;
import static io.vertx.ext.web.Router.router;
import static java.util.ServiceLoader.load;

public class ${_mainModule}
    extends AbstractModule
{
  @Override
  protected void configure() {

    // Inject all Guice modules found via SPI

    final ServiceLoader<Module> modules = load(Module.class);
    modules.forEach(this::requestInjection);
    modules.forEach(this::install);

    // TODO: file/env based way to give Vertx runtime options
    // TODO: file/env based way to bind an object with verticle deployment options.

    VertxOptions options = new VertxOptions();
    final Vertx vertx = vertx(options);
    bind(Vertx.class).toInstance(vertx);
    bind(Router.class).toInstance(router(vertx));

    // Inject verticles discovered via SPI

    final ServiceLoader<Verticle> verticles = load(Verticle.class);
    verticles.forEach(this::requestInjection);
    bind(new TypeLiteral<ServiceLoader<Verticle>>() {
    }).toInstance(verticles);

    // Inject Vertx Shell command resolvers discovered via SPI

    final ServiceLoader<CommandResolver> commandPacks = load(CommandResolver.class);
    commandPacks.forEach(this::requestInjection);
    bind(new TypeLiteral<ServiceLoader<CommandResolver>>() {
    }).toInstance(commandPacks);

  }
}
