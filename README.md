# *Protify - Music Player*

## Project created and maintained for the SKW - Frameworks course

## Technologies used
- Spring & Spring Boot
- PostgreSQL
- Swagger
- Remix

## Team
- Łukasz Bociański
- Gabriel Maruszewski
- Patryk Wałach

## Initialization

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

## TODO

- Add DB models and test data
- Add SwaggerUI
- Add basic endpoints
- Create tests
- Check Azure Blob Storage
 
 ![Testy](https://pbs.twimg.com/media/CiLWjAQVEAIae7z.jpg)
