Perfect! Since you want a **simple update** to reflect your new Spring Security + JWT changes, we can revise your original README like this:

---

# Blog Application (Spring Boot + JPA + JWT Security)

A simple Blog REST API built using **Spring Boot**, **Spring Data JPA**, and **MySQL**.
Supports **Users, Posts, and Comments** with CRUD operations.

### New Features

* JWT-based authentication
* Role-based authorization (Admin/User)
* Secured endpoints with Spring Security

### Setup

1. Clone the repo

```bash
git clone <your-repo-url>
cd BlogApplication
```

2. Configure **local `application.properties`** (not in repo) with your DB & JWT secret:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=yourSecretKey
```

3. Run the app

```bash
mvn spring-boot:run
```

4. Test APIs using Postman or curl with JWT token authentication.

