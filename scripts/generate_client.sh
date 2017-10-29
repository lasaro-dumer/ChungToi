cd CTClient/src/main/java/
rm -rf chungtoi/client/proxy
wsimport -keep -p chungtoi.client.proxy http://localhost:8080/ctwebservice/ChungToiWS?wsdl
cd ../../../
mvn clean install | grep -e '^\[[[:alpha:]]'
