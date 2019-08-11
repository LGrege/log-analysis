# Sample Application

Demo application to show the capabilities, extensibility and ease of use of the framework.

## Overview

The sample application uses the framework provided within the [Anomaly Detector](../anomalyDetector), enhancing it
on multiple key points to demonstrate ease of extensibility. Inclusion of the framework into this application can be
done by specifying it as dependency within the `pom.xml` of the sample project:

```xml
<dependency>
  <groupId>com.lukasgregori.ml</groupId>
  <artifactId>anomaly-detector</artifactId>
  <version>${project.version}</version>
</dependency>
```
### Backend

In order to store away found clusters aswell as anomalies for later usage / analysis once the application is shut down,
the `MongoDBLogEventMonitor` class has been created, overwriting the OOTB LogEventMonitor that comes within the framework.
Since this project makes usage of Spring and all important beans have been wired using an alias, extensions to the
core framework can be done with ease within the `application-config.xml` found in the resource folder.

```xml
<!-- Log Event Monitor Persisting to Mongo-DB -->
<alias name="mongoDBLogEventMonitor" alias="logEventMonitor"/>
<bean name="mongoDBLogEventMonitor" class="com.lukasgregori.ml.mongo.MongoDBLogEventMonitor"/>
```

### Frontend

To visualize the results provided by the Backend (i.e. the running Spring Boot application), an Angular
project was created. Within this project clusters and their information are displayed within custom components:

![Angular Sample Application](https://github.com/LGrege/LogAnalysis/blob/master/documentation/images/angular.png?raw=true "Title")

In order to provide data from the backend to the Angular application, a simple controller was written, capable of
serving `SOClusterDAO` objects to the frontend, containing all needed information to represent a cluster.
 
## Usage

#### Configuration

All configuration needed is done within the `application.properties` file,
which can be found within the resource folder of the project:

```
### Logging ###
spring.main.banner-mode=off
logging.pattern.console=%clr(%level %thread [%logger{1}] - %message%n)
logging.level.root=ERROR
### Input Parsing Configuration ###
input.file.url=
input.logging.format=TIMESTAMP LEVEL THREAD [LOGGER] - MESSAGE
input.timestamp.format=yyyy-MM-dd HH:mm:ss,SSS
input.tailing=false
sliding.window.size=20
feature.vector.dimensions=10
so.clusterer.merge.distance=0.001f
anomaly.detection.frequency=10
pb.anomaly.threshold=0.995f
### Mongo DB ###
db.collection.anomalies=anomalies
db.collection.clusters=clusters
db.name=anomalyDB
db.host=localhost
```

##### Property mapping

###### Input Parsing

Input parsing based on [FileInputParser](https://github.com/LGrege/LogAnalysis/blob/master/anomalyDetector/src/main/java/com/lukasgregori/ml/input/parser/impl/FileInputParser.java).
Additional information on patterns to use and sample configurations can be found at [LogFilePatternReceiver](https://logging.apache.org/chainsaw/2.x/apidocs/org/apache/log4j/varia/LogFilePatternReceiver.html).

| Property               | Description                                            | Example                                         |
| :---                   | :---                                                   | :---                                            |
| input.file.url         | Path to used log file                                  | ```file:///home/bob/Desktop/sample.log```       |
| input.logging.format   | SLF4J / LOG4J pattern of the file to be parsed         | ```TIMESTAMP LEVEL THREAD [LOGGER] - MESSAGE``` |
| input.timestamp.format | Timestamp format used                                  | ```yyyy-MM-dd HH:mm:ss,SSS```                   |
| input.tailing          | Determines if log file shell be monitored or read once |  ```true```                                     |

###### Clustering

| Property                    | Description                                                                             | Example      |
| :---                        | :---                                                                                    | :---         |
| sliding.window.size         | Window sized used for TF-IDF algorithm when transforming log lines to numerical vectors | ```20```     |
| feature.vector.dimensions   | Length of feature vector dependent on algorithm used                                    | ```10```     |
| so.clusterer.merge.distance | Cluster proximity threshold under which two clusters will be merged                     | ```0.001f``` |
| anomaly.detection.frequency | Every *n* lines read from log file, anomaly detection will be triggered                 | ```10```     |
| pb.anomaly.threshold        | Outlier threshold used for determining when event is outside of usual boundaries        | ```0.995f``` |


###### Mongo DB

| Property                | Description                       | Example         |
| :---                    | :---                              | :---            |
| db.collection.anomalies | Collection for storing anomalies  | ```anomalies``` |
| db.collection.clusters  | Collection for storing clusters   | ```clusters```  |
| db.name                 | Name of database to be used       | ```anomalyDB``` |
| db.host                 | Name of host where DB is running  | ```localhost``` |
