### Building

You must use Maven to build the project. In the same directory of this readme file is the main ``pom.xml``, in this directory you should run:

	mvn clean install

You can also open the projects in Netbeans and build from there.

### Using

The resulting ``JARs`` will be in the each projects ``target`` directory.

The command to run the server:

	java -jar CTServer\target\CTServer-1.0-jar-with-dependencies.jar

The command to run the client:

	java -jar CTClient\target\CTClient-1.0-jar-with-dependencies.jar localhost

Alternatively you can use the ``.bat`` (or ``.sh``) to run each instance.
