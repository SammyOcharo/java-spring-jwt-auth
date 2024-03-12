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

[Step 2(Create UserRepository Interface)](#entityRepositorycreation)
- We create a UserRepository that extends the extends the JpaRepository interface, which is part of the Spring Data JPA framework. JpaRepository is a generic interface that provides CRUD (Create, Read, Update, Delete) operations for entities. In this case, the entity type is User, and the primary key type is Long.
- The @Repository annotation is used to indicate that the interface is a Spring Data repository. It is a specialization of the @Component annotation, allowing Spring to automatically detect and manage the bean.
- Additionally we create a method, Optional<UserDetails> findByEmail(String email);
- The method will check our database and check wether the user with  the email address passed exists. Before authentication.
- Here is the snippet.

[Step 3(UserAuth Service class Creation)](#entitycreation)
- We create a service class under Service impl. This service class will implement the UserDetailService interface. 
- The UserDetailsService interface is provided by Spring Security. It declares a single method, loadUserByUsername, which is responsible for loading user details by username during the authentication process.
- Here is the snippet.

[Step 4(JWT Service class Creation)](#JWTcreation)
- This class will be responsible for generating token, claims extraction and checking validity of token.
- The following methods are in the jwtservice class.
    [1]
- We start with token generation
    
    ##snippet

  
- The `generateToken` method is a public function designed to take a `User` object as its input and return a String.
- The primary objective of this method is to create a JSON Web Token (JWT) based on the information provided within the user object.
- The JWT generation process involves several steps.
- Firstly, the method employs `Jwts.builder()` to initiate the construction of the JWT.
- Subsequently, it sets the subject of the JWT to the username of the given user using `.subject(user.getUsername())`. The method then establishes the issuance time of the JWT as the current time with `.issuedAt(new Date(System.currentTimeMillis()))`.
- Additionally, it sets the expiration time of the JWT to be 24 hours from the current time through the use of `.expiration(new Date(System.currentTimeMillis() * 24 * 60 * 60))`.
- The JWT is further secured by signing it with a key, as denoted by `.signWith(SignWithKey())`.
- Finally, the method concludes the JWT creation process with `.compact()`, which builds the final compact JWT string.

  [2]
- snippet
- 
  The `SignWithKey` method is a private function responsible for creating a secret key used in signing a JSON Web Token (JWT). Initially, the method decodes the provided `secret` variable, which holds a secret key in base64 URL-encoded format, into a sequence of bytes. Subsequently, it utilizes the `Keys.hmacShaKeyFor` function to generate a secure HMAC (Hash-based Message Authentication Code) key using the decoded byte array. This HMAC key is crucial for ensuring the integrity and authenticity of the data during the process of creating a JWT.

[3]

  

# Usage
- [API Endpoints](#api-endpoints)
- [Authentication and Authorization](#authentication-and-authorization)
# Error Handling
# Testing
# Contributing
# License
