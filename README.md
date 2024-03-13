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
- The following methods are in the jwtservice class.<br>
    [1]    
![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/generate_token.png)
  - We start with token generation  
  - The `generateToken` method is a public function designed to take a `User` object as its input and return a String.
  - The primary objective of this method is to create a JSON Web Token (JWT) based on the information provided within the user object.
  - The JWT generation process involves several steps.
  - Firstly, the method employs `Jwts.builder()` to initiate the construction of the JWT.
  - Subsequently, it sets the subject of the JWT to the username of the given user using `.subject(user.getUsername())`.
  - The method then establishes the issuance time of the JWT as the current time with `.issuedAt(new         Date(System.currentTimeMillis()))`.
  - Additionally, it sets the expiration time of the JWT to be 24 hours from the current time through the use of `.expiration(new Date(System.currentTimeMillis() * 24 * 60 * 60))`.
  - The JWT is further secured by signing it with a key, as denoted by `.signWith(SignWithKey())`.
  - Finally, the method concludes the JWT creation process with `.compact()`, which builds the final compact JWT string.

  [2]
![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/signWithKey.png)
   - The `SignWithKey` method is a private function responsible for creating a secret key used in signing a JSON Web Token (JWT).
   - First, the method decodes the provided `secret` variable, which holds a secret key in base64 URL-encoded format, into a sequence of bytes.
   - Then, it utilizes the `Keys.hmacShaKeyFor` function to generate a secure HMAC (Hash-based Message Authentication Code) key using the decoded byte array.
   - This HMAC key is crucial for ensuring the integrity and authenticity of the data during the process of creating a JWT.

    [3]
  ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/extractAllClaims.png)
   - The `extractAllClaims` method is designed to take a jwt token,  as input and retrieve the embedded claims within that token.
   - This process involves using the functionalities provided by the `Jwts` class, a library for handling JWTs.
   - The method starts by initializing a JWT parser with `.parser()` and then specifying that the token's signature should be verified using a secret key generated by the `SignWithKey` method with `.verifyWith(SignWithKey())`.
   - After setting up the parser, it proceeds to parse the signed claims from the token using `.parseSignedClaims(token)`.
   - Finally, it retrieves and returns the payload, which contains the data and claims of the token, using `.getPayload()`.
   - In simpler terms, this method ensures that the token is valid, verifies its signature, and then extracts and provides access to the information (claims) stored within the token.

    [4]
   ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/sxtractClaim.png)
    - The `extractClaim` method is a versatile function designed to retrieve specific information (claims) from a given token, possibly a JSON Web Token (JWT).
    - It takes two inputs: the token itself and a resolver function. The method calls, `extractAllClaims`,method to gather all the available claims from the provided token.
    - Subsequently, it applies the resolver function to these claims, allowing for the extraction of a particular piece of information.
    - In simpler terms, this method acts as a helpful tool to fetch specific details from a token by utilizing a resolver function tailored to the desired type of information.
 
    [5]
  ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/extractUsername.png)
  - The `extractUsername` method serves as a simple function that takes a token, as input and focuses on extracting the username from it.
  - Internally, it utilizes the more general `extractClaim` function, which I discussed earlier.
  - By passing in the token and specifying a resolver function as `Claims::getSubject`, the method specifically targets the "subject" claim within the token.
  - This claim typically holds information such as the username or user identifier.
  - In essence, `extractUsername` provides a convenient way to isolate and retrieve the username from a token, catering to scenarios where user
  
  [6]
![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/isTokenValid.png)
  - The `isTokenValid` method checks the validity of a given token for a specified user. Initially, it extracts the username (in this case an email) from the token using the `extractUsername` function.
  - Subsequently, the method compares this extracted username with the username of the provided `UserDetails` object.
  - If the two usernames match, the method concludes that the token is valid for the particular user, and it returns `true`.
  - Conversely, if the usernames do not match, the method returns `false`, indicating that the token is not valid for the provided user.
  - Essentially, this method aids in verifying the integrity of a token in relation to a specific user in the system.
 
  [7]
 ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/IsTokenExpired.png)
  - The `isTokenExpired` method checks whether a given token has expired.
  - It accomplishes this by using method, `extractExpiration`, to retrieve the expiration date from the token using the `extractClaim` function.
  - The `Claims::getExpiration` function is specified to extract the expiration date claim.
  - Then, the method compares the extracted expiration date with the current date, returning `true` if the token has expired (if the expiration date is before the current date) and `false` otherwise.
  - Essentially, `isTokenExpired` simplifies the process of checking the validity of a token based on its expiration status.
 
[Step 5(Filter class Creation)](#FilterClassCreation)
 ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/securityFilter.png)
- Under the config package we create a SecurityFilter class.
- The purpose of this filter is to intercept incoming HTTP requests. This is possible by extending to the OncePerRequestClass.
- It is annotated with "@Component," indicating that it is a Spring component and can be automatically detected and used by the Spring framework.
- The purpose of this filter is to intercept incoming HTTP requests and check for a specific type of header called "Authorization."
- If the header is present and starts with "Bearer," it extracts a JWT (JSON Web Token) from the header, validates it, and if valid, sets up the user authentication in the Spring Security context.
- The class has two dependencies injected through its constructor: "JwtService" and "UserAuthService." These services are responsible for handling JWT-related operations(from our earlier jwt service class) and user authentication, respectively.
- The "doFilterInternal" method, overridden from the parent class, is where the main logic happens.
- It checks if the Authorization header is present and starts with "Bearer."
- If true, it extracts the JWT, gets the user email from it, and validates the token using the injected services.
- If the token is valid and the user is not already authenticated, it creates a Spring Security authentication token and sets it in the SecurityContextHolder.
- Finally, the filter chain is continued using "filterChain.doFilter(request, response)," allowing the request to proceed to the next filters or the actual endpoint.

[Step 6(SecurityConfig class Creation)](#SecurityConfigClassCreation)
 ![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/securityConfig.png)
- The SecurityConfig class lies in its role as a central configuration hub for managing security aspects within a Spring-based web application.
- The `securityFilterChain` method, annotated with `@Bean`, within the provided `SecurityConfig` class is crucial for defining the security filter chain in a Spring Security-enabled web application.
- This method, taking an `HttpSecurity` parameter, having a comprehensive set of security configurations. Firstly, it disables Cross-Site Request Forgery (CSRF) protection, indicating suitability for stateless scenarios like APIs.
- The subsequent configuration, employing the `authorizeHttpRequests` method, establishes rules for request authorization.
- Requests matching patterns such as "/apps/api/v1/user/**" and "/apps/api/v1/user/login/" are permitted without authentication, while any other request requires authentication.
- Furthermore, the method sets the session creation policy to be stateless, aligning with the principles of stateless RESTful APIs.
- It integrates a custom authentication provider, configured in the `authenticationProvider` method, and adds a custom security filter before the standard `UsernamePasswordAuthenticationFilter`.
- The resulting `SecurityFilterChain` encapsulates the entirety of these configurations, representing a robust security framework for the Spring Security-enabled web application.

    
# Usage
- [API Endpoints](#api-endpoints)
- The project has 3 endpoints<br>
  [1. Register User](#RegisterUser)<br>

![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/postman_register.png)

- otp sent to email<br>
![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/email_otp.png)
  
  [2. Verify User](#VerifyUser)<br>

![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/postman_verify_account.png)

  
  [3. Login User](#LoginUser)<br>
![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/user_login.png)

![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/mysql_user.png)


[1]
-Using our DAO class we will register the user. Testing on postman to check on the responses.

  
# Error Handling
- Here is some of the custom errors used in the project. When it comes to error handling custom errors serve more purposes to communicate more precisely to the end user
![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/OtpMissMatch.png)

![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/UserAlreadyExists.png)

![Spring Boot Logo](https://github.com/SammyOcharo/java-spring-jwt-auth/blob/main/src/main/java/com/samdev/jwt_auth/images/UserDoesNotExist.png)

# Contributing

Thank you for considering contributing to our project! Whether you want to report a bug, propose a feature, or contribute code, your help is welcome.

### Bug Reports and Feature Requests

If you encounter any issues or have ideas for improvements, please open an issue on our [issue tracker](link-to-issue-tracker). When creating an issue, please provide detailed information about the problem or feature request, including steps to reproduce the issue.

### Development Setup

To set up the project for development:

1. Fork the repository and clone it locally.
2. Install [Java](https://www.oracle.com/java/technologies/javase-downloads.html) and [Maven](https://maven.apache.org/download.cgi) if you haven't already.
3. Build the project using Maven: `mvn clean install`.
4. Import the project into your favorite IDE.

### Coding Guidelines

Before contributing code, please make sure to follow the correct coding guidelines. 

### Creating Pull Requests

1. Create a new branch for your contribution: `git checkout -b feature/new-feature`.
2. Make your changes and ensure they pass all tests.
3. Commit your changes with a clear and descriptive message: `git commit -m "Add new feature"`.
4. Push your changes to your fork: `git push origin feature/new-feature`.
5. Open a pull request against the `main` branch.

# License
- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
