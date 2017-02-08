# OracleCloudStorageForJ

##Runing it
* mvn package
* mvn exec:java -Dexec.mainClass="com.ocs.OCSUser.App"  -Dexec.args="-u email -p  password -s <serviceName> -c <container> -o <operation>"

## Operations with -c option
* listContainers
* createObject 
* createContainers

##Examples

 mvn exec:java -Dexec.mainClass="com.ocs.OCSUser.App"  -Dexec.args="-u email -p  password -s <serviceName> -c container1 -o createContainers"
 mvn exec:java -Dexec.mainClass="com.ocs.OCSUser.App"  -Dexec.args="-u email -p  password -s <serviceName> -c container1 -o createObject"
 mvn exec:java -Dexec.mainClass="com.ocs.OCSUser.App"  -Dexec.args="-u email -p  password -s <serviceName> -o list_"



