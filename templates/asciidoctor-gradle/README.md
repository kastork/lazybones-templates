asciidoctor-gradle template
---------------------------

You have just created a basic writing project that uses
asciidoctor to render your asciidoc markup into various
rendered forms.  The rendering process is executed by
running gradle tasks.

* Edit your files in `src/asciidoc`
* If you have artwork source files (like Photoshop or Omnigraph files) that you want to keep in your project under version control, the `src/artwork` directory is provided.  Save your page-ready art from your image software into `src/resources/images`

By default, the gradle file will render your project into html5 and pdf.  Render your project:

Windows
=======

    .\gradlew.bat asciidoctor

Everywhere else in the known universe
=====================================

    ./gradlew asciidoctor

