Requires [Apache Maven](http://maven.apache.org) 3.0 or greater, and JDK 6+ in order to run.

To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine

    mvn clean install
Run the application with mvn appengine:devserver, and ensure it's running by visiting your local server's address (by default localhost:8080.)
Get the client library with mvn appengine:endpoints_get_client_lib (it will generate a zip file named helloworld-v1-java.zip in the root of your project.)
Deploy your application to Google App Engine with mvn appengine:update
