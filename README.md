# Trio  
### Trio's Code Challenge

### Stack:
| Technology | Version |
|--|--|
| **Java** | 11.0.3-2018-01-14 |
| **Spring Boot** | 2.7.5.RELEASE |
| **Project Lombok** | 1.18.24 |
| **BananaJ Java Mailchimp API** | 0.6.4 |
| **JUnit 4/5** | 4.1.5 - 5.6.2 |
| **Model Mapper** | 3.1.0 |
| **Mockito** | 4.8.1 |
| **H2 Memory** | 2.1.212 |
| **Springfox Swagger 2** | 2.9.2 |

### Acess Swagger Open Rest API:
Once with the application running:

- Localhost: http://localhost:8080/swagger-ui.html  
- Heroku: https://trio-backend-challenge.herokuapp.com/swagger-ui.html  
- Amazon: http://ec2-52-91-81-26.compute-1.amazonaws.com:8080/swagger-ui.html

### Acess H2 Memory Database:
Once with the application running:

- Localhost: http://localhost:8080/h2-console
- Heroku: https://trio-backend-challenge.herokuapp.com/h2-console
- Amazon: http://ec2-52-91-81-26.compute-1.amazonaws.com:8080/h2-console

jdbc: `jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE;DATABASE_TO_UPPER=false;`  
user: `admin`  
pass: `admin`

### How to run the application:

Localhost:  
- IDE (IntelliJ, Eclipse, NetBeans):
- Import the project as a Maven project on your favourite IDE.
- Build project using Java 11
- Run/Debug project from Main Application Class :: TrioBackendChallengeApplication

Terminal:
- `mvn spring-boot:run`

### How to run the tests:

Terminal:
- `mvn test`

### APIs:

**Localhost** base URL path: http://localhost:8080/  
**Heroku** base URL path: https://trio-backend-challenge.herokuapp.com/  
**Amazon** base URL path: http://ec2-52-91-81-26.compute-1.amazonaws.com:8080/

* GET: `/contacts/sync`
> Should return the list of synced contacts in JSON following this structure:

```javascript{
{
   "syncedContacts": 1, // total synced contacts
   "contacts":[
      {
         "firstName":"Amelia",
         "lastName":"Earhart",
         "email":"amelia_earhart@gmail.com"
      }
   ]
}
```

### Mailchimp:

- API Key: `e054a920262868edbcb1acfc2c13a68e-us21`
- Default List (Samuel Catalano) Id: `ed1c7ae58d`
