# Cafe Management

A simple project based on Java 11, Spring Boot, MySQL, JUnit 5, and Testcontainers.

## Getting Started

These instructions will get you a copy of the project and run on it your local machine for development and testing
purposes. See deployment for notes on how to deploy the project on a live system.

What things you need to run the project:

```
Java Development Kit (JDK 11 or newer)
Maven
Docker (if you want to run the application using the Docker image)
```

### Installing

Clone the repository:

```
git clone https://github.com/rezaep/cafe-management
```

Use Maven build tool to compile and build the project:

```
mvn clean compile
```

### Running the tests

#### Run tests

To run unit and integration tests use the following command:

```
mvn verify
```

### Running the application

#### Run using Java

To run the application using Java, run the following command:

```
java -jar target/cafe-manager-1.0.0.jar
```

#### Run using Maven and Spring Boot plugin

To run the application using Spring boot maven-plugin, run the following command:

```
mvn spring-boot:run
```

#### Run using Docker

To package the Jar file inside a Docker image, use the following commands:

```
mvn clean package
docker build -t image:tag . (e.g. rezaep/cafe-management:latest)
```

To run the application using Docker, run the following command:

```
docker run -p 8080:8080 image:tag (e.g. rezaep/cafe-management:latest)
```

#### Run using Docker Compose

To run the application using Docker Compose, run the following command:

```
docker-compose up -d
```

### Database configurations

To change default database configurations based on deployment way, update [docker-compose.yml](docker-compose.yml)
or [application.yaml](src/main/resources/application.yaml) file.

````
url: "jdbc:mysql://localhost:3306/cafe"
username: "user"
password: "password"
````

### Start working with APIs

Swagger generates documentation of REST APIs, which is accessible using
this [link](http://localhost:8080/swagger-ui/index.html).

### Authentication

The `Basic Auth` method is used for simplicity in the authentication process. When the application runs for the first
time, it creates a very first manager user with the following credentials:

````
username: admin
password: admin
````

## Authors

* **Reza Ebrahimpour** - [Github](https://github.com/rezaep)

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details