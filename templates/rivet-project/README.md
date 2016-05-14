# Starter Rivet Application

Creates a root project with a sample application.

The Application doesn't do anything, but you can run it to
prove the template works.

    gradle runShadow
    
The application becomes useful by detecting Verticles, 
Command Packs and Guice Modules with the Java service 
provider interface (SPI) found on the classpath.

To make the sample app useful, use the lazybones generators
to create sub-projects that are pre-configured to be
detected and deployed

Keep in mind that these sub-projects all need to be placed
on the classpath of the application in order to be deployed.
The lazybones template doesn't do that for you.  The easiest 
way to do that is by adding a gradle project dependency in
the build file in the sample application's project.

* rivet-webserver    : (Verticle) A vertx-web server 
(TBD)                : wired up with the main Router
* rivet-shell        : (Verticle) A vertx-shell serving
(TBD)                : on telnet localhost 4000
                     : And, if route-webserver exists,
                     : then on that server at `/shell`
                     : Install commands by generating 
                     : rivet-command-packs that
                     : implement what you need.
* rivet-command-pack : (Guice Module) Starter sub-project
(TBD)                : for vertx-shell command packs.
                     : You can have as many command-pack 
                     : sub-projects as you need for different
                     : things.
* rivet-subrouter    : (Verticle) A starter sub-project for
                     : web endpoints. You can have as many
                     : subrouter projects as you need. Each
                     : one is mounted at a unique mount point
                     : relative to the root and has a static
                     : handler for its own assets.
