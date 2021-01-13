# Todo React
This is the backend repository built using SpringBoot, Spring security, and JPA. This app is yet another basic todo app. This app is deployed on heroku located here: https://jvo-todo-react.herokuapp.com/

Ideas Explored:
- REST API
- SpringBoot
- User authentication flow (JWT)
- Relational databases
- (soon) testing

# Build
To build, please clone the submodule [frontend](https://github.com/jvogit/todo-react-frontend/) into this repository (or other suitable react frontend implementations).
```java
gradlew -PbuildType=prod build
```
will automatically install a local version of npm and node in order to build frontend react app and include it into the final jar.

The dev build type will use h2 in memory database, enable h2 console, insert initial data, usually for development purposes.

The prod build type will use Postgresql as the database. Credentials must be provided as environment during runtime (according to application-prod.properties file)

The jar can be run like any other SpringBoot app.
```java
java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar todo-react-1.0.0.jar
```
This repository includes two SpringBoot profiles: dev (development) and prod (production).
This corresponds to the build type used to build in gradle.

Additionally this can be deployed to heroku. Modify the gradlew build command used by heroku via GRADLE_TASK or similar as detailed [here](https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku#overview).
The Postgresql add-on is needed.
```
heroku config:set GRADLE_TASK="-PbuildType=prod build"
```

# Features:
- Basic user authentication (Basic JWT)
- Keep track of daily todos
- Edit todos' completion, order, and text
- Dark mode and light mode!
- Mobile friendly

