# Web Verticle Service Template

Creates a gradle-based Vertx verticle project.

The verticle requires injection of a reference to a Vertx Router instance, and 
defines an endpoint by attaching a sub-router to that instance.  The name of
the endpoint is configurable.