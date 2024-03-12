# Prerequisites
- [Maven Dependencies](#installation)
- On [Spring initializer](https://start.spring.io/)
- We need the following dependencies to get us started.
  1. Spring security
  2. Spring web
  3. Spring data jpa
  4. Spring dev tools
  5. MySQL driver(I will be using MYSQL db for storage.)
- From the [Maven repository](https://mvnrepository.com/)
- We also need the following depencies to help with JsonWeb Token
  1. JJWT::API
  2. JJWT::IMPL
  3. JJWT:: JACKSON
- The 3 libraries will help with token generation, extracting claims from the token and much more we will get to see in the project.
  
- [Configuration](#configuration)
- After inclusion of all the depencies and downloaded the zip folder. Unzip it and open on your IDE of choice. In this project am going to work with Intellij Idea.
- All dependencies should be available on the project's pom.xml file.
- Because we are working with MYSQL, spring expects us to add configuration that will be used by Spring data JPA in connecting to the database.
- Here is a snippet of the configuration file(application.properties) located under src/main/java/resources/application.properties
- On the (spring.datasource.username) and (spring.datasource.username) you will add the correct mysql username and passoword set on your local machine
- ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/application_properties.png)
- Under src/main/java/com.samdev.jwt_auth we make the following packages
  1. Config
  2. Controller
  3. DAO
  4. Entity
  5. Exceptions
  6. Repository
  7. Service
  8. Service/Impl

[Step 1(Entity Creation)](#entitycreation)
- We will create our User entity class in the entity package. This user entity class will have member variables that will create columns in the database. Our entity class will entail the following fields
  1. id
  2. email
  3. mobileNUmber
  4. role
  5. password
  6. isAccountActivated.

We then implement the UserDetails interface. Spring security provides an interface called UserDetails Interface. In the context of Spring security the interface is critical as it provides.
- Reliance. Spring Security relies on the UserDetails interface to obtain user information during the authentication process. This flexibility enables you to integrate your user management system seamlessly with Spring Security, regardless of whether you are using a database, LDAP, or any other authentication provider.
- Role-Based Access Control:
The getAuthorities() method in the UserDetails interface returns a collection of GrantedAuthority objects, representing the roles or authorities associated with the user.
- Additional Control:
The UserDetails interface includes methods such as isEnabled(), isAccountNonExpired(), isAccountNonLocked(), and isCredentialsNonExpired(). Implementing these methods allows you to exert fine-grained control over the status of user accounts, such as enabling or disabling accounts, enforcing password expiration policies, and more.
- There are more functionalities provided by the userDetails Interface (I will write an article on the sam soon).
- Here is a snippet of the class.
 ##Picture here

# Usage
- [API Endpoints](#api-endpoints)
- [Authentication and Authorization](#authentication-and-authorization)
# Error Handling
# Testing
# Contributing
# License
