package ${_appPackage};

import io.vertx.core.Context;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.inject.Guice.createInjector;

public class ${_mainClass} implements Runnable
{
  static Logger logger = LoggerFactory.getLogger(${_mainClass}.class);
  private final Vertx vertx;
  private final ServiceLoader<Verticle> verticles;
  private JsonObject deploymentOptions;
  private Map<String, Verticle> deployedVerticles;

  @Inject
  public ${_mainClass}(final Vertx vertx,
                 final ServiceLoader<Verticle> verticles)
  {
    this.vertx = vertx;
    this.verticles = verticles;
    deployedVerticles = new ConcurrentHashMap<>();
  }

  public static void main(final String... args)
  {
    createInjector(new ${_mainModule}()).
    getInstance(${_mainClass}.class).run();
  }

  @Override
  public void run()
  {
    logger.info("Deploying verticles wired via SPI");
    verticles.forEach((verticle) -> {
        logger.info("trying: " + verticle.getClass().getCanonicalName());
        vertx.deployVerticle(verticle, (result) -> {
        if(result.succeeded()) {
          String verticleType = verticle.getClass().getCanonicalName();
          String deploymentId = result.result();
          deployedVerticles.put(deploymentId,verticle);
          logger.info(verticleType + " : " + deploymentId);
          logger.info("Deployed Verticles: " + deployedVerticles.size());
        } else {
          logger.warn("deployment failure: " + result.cause().toString());
        }
      });
    }
    );
  }

}
