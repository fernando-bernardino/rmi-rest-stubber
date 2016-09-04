Cloudbase Content Server

Application responsible for retrieve and maintain the users workspace configuration. The application uses Spring Boot and Maven and is accessible through a RESTful API.

* Project structure:
	/src				all source code
		/src/main		production code and resources
		/src/test		test code and test resources

	/config				configuration files for application
		application.properties	application properties
		log4j.properties	log configuration

* To build the project:

	- mvn install
		compiles the project, runs the unit tests and builds the artifact
		
	- mvn install -P integration
		runs the integration tests (tests ending in IT.java and in the /src/test/java)
		
	- mvn install -P acceptance
		runs the acceptance tests (tests in sub-packages of com.deontics.css.cucumber the /src/test/java)

* To run the project:
	- mvn spring-boot:run
