# OracleCloudStorageForJ

##Runing it
* mvn package
* mvn exec:java -Dexec.mainClass="com.ocs.OCSUser.App"  -Dexec.args="-u email -p  password -s <serviceName> -c <container> -o <operation>"

## Operations with -c option
* listContainers
* createObject 
* createContainers

##Examples
* mvn exec:java -Dexec.mainClass="com.ocs.OCSUser.App"  -Dexec.args="-u email -p  password -s <serviceName> -c foobar -o foo"
