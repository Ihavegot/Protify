# *Protify - Music Player*

## Technologies used
- Spring Boot
- PostgreSQL
- Swagger
- Spring Security

## Team
- Łukasz Bociański
- Gabriel Maruszewski
- Patryk Wałach

### More info about project in [doc](doc/README.md)

---

## Docker PostgreSQL

*Docker initialization command*

```
docker run --name protify -e POSTGRES_PASSWORD= -e POSTGRES_DB=protify -e POSTGRES_HOST_AUTH_METHOD=trust -p 32770:5432 -d postgres
```

*H2 Console*

Go to: http://localhost:8080/h2-console

- **Saved Settings:** Generic PostgreSQL
- **Setting Name:** Generic PostgreSQL
- **Driver Class:** org.postgresql.Driver
- **JDBC URL:** jdbc:postgresql://localhost:32770/protify
- **User Name:** postgres
- **Password:**

## Protify API

*Application runs on local machine. After start you can navigate to:*

- http://localhost:8080/

*or*

- http://localhost:8080/swagger-ui.html



