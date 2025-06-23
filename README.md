## Настройка PostgreSQL в Docker
Приложение настроено на подключение к PostgreSQL по следующим параметрам:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/testdb
spring.datasource.username=postgres
spring.datasource.password=astondb
```
Путь до эндпоинта:
````
http://localhost:8080/api/users
