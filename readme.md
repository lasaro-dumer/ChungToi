### Pre requirements
* Maven
* GlassFish

	* Must be located at `~/GlassFish_Server/glassfish` in order for the scripts to work

### Building

You must use Maven to build the project. Then you simply run:

```bash
	ChungToi$ mvn clean install
```

You can also open the projects in Netbeans and build from there.

The results of the build will be:
* `JAR` file for the client application, with the path `CTClient/target/CTClient-*.jar`
* `WAR` file of the Web Service, with the path `CTServer/target/CTServer*.war`

### Using

#### Interactive Mode
In Interactive Mode you will be able to play the game as the in the previous version using RMI.

The command to run the client in Interactive Mode (using the `JAR` packed with the dependencies):

```bash
	ChungToi$ java -jar CTClient\target\CTClient-1.0-jar-with-dependencies.jar
```

#### Batch Mode
In Batch Mode the client will need one of the following inputs:

* Folder path, which contains `.in` files, all of them will be processed with no specific order
* A path to a `.in` file, the file will be processed
* A path to a `.lst` file, this is a `List File` and all the `.in` files listed will be processed

Some examples are:
```bash
	ChungToi$ java -jar CTClient/target/CTClient-1.0-jar-with-dependencies.jar samples

	ChungToi$ java -jar CTClient/target/CTClient-1.0-jar-with-dependencies.jar samples/ChungToi-0000.in

	ChungToi$ java -jar CTClient/target/CTClient-1.0-jar-with-dependencies.jar samples/Batch1_List1.lst
```
#### The Cloud Switch

The Web Service is already deploy in a Azure VM, so you can call it using this [WSDL URL](http://solr-impbd.eastus.cloudapp.azure.com:8080/ctwebservice/ChungToiWS)

To use the cloud endpoint in the client you can send the `-c` argument (in any mode and with any input file/folder), as shown in the example

```bash
	ChungToi$ java -jar CTClient/target/CTClient-1.0-jar-with-dependencies.jar -c [file/folder]
```
