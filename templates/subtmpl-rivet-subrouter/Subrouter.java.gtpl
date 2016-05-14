package ${_package};

import io.vertx.core.Verticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.handler.StaticHandler;
import org.kohsuke.MetaInfServices;

import javax.inject.Inject;
import javax.inject.Singleton;

@MetaInfServices(Verticle.class)
@Singleton
public class ${_class} extends AbstractVerticle
{
  @Inject
  private Router router;

  @Override
  public void start()
  {
    Route route = router.route("/${_mountPoint}");
    StaticHandler
        handler = StaticHandler
        .create()
        .setAllowRootFileSystemAccess(false)
        .setWebRoot("${_package}")
        .setDirectoryListing(false);
    route.handler(handler);
  }
}
