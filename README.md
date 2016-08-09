# CMPE295B-CampusTribune
Application Server for Campus Tribune Mobile App

```
	Developed using Java, Springboot, REST APIs, Java Messaging Services, Spring data mongodb and spring security frameworks
	Uses MongoDB NoSQL data base
	Provides Token based authentication system
	Uses Google Cloud Messaging for notification services
```

Git Guidelines for contributors:

```
git add .
git commit -m "commit message"
git pull origin <branchName/master>
git push origin master

```
```

Tools used to run the application:
	Java 8
	Gradle Plugin
	MongoDB

```

```
Pre-configuartions:
	Install MongoDB on localhost or Amazon EC2 or create database on Mongolab
	Add the mongo server port in the application.properties resource file.
		File path: /campusTribune/src/main/resources/application.properties
		For localhost:
			#spring.data.mongodb.host = localhost
			#spring.data.mongodb.port = 27017
			#spring.data.mongodb.repositories.enabled = true
			#spring.data.mongodb.database = <dbname>
		For mongolab:
			#mongodb://<dbuser>:<dbpassword>@<dbid>.mlab.com:<dbport>/<dbname>
		For EC2:
			#spring.data.mongodb.host = <EC2 instance DNS>
			#spring.data.mongodb.port = <port number>
			#spring.data.mongodb.database = <dbname>
			
 GCM Server API Key:
  Set the server API key for GCM Service corresponding to the google-services.json client file.
  Filr Path : CMPE295B-CampusTribune/src/main/java/com/ct/notifications/NotificationSystem.java

```
```
Commands to run the app from terminal:
	gradle build
	gradle bootRun

```









