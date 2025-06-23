## Настройка PostgreSQL в Docker
Приложение настроено на подключение к PostgreSQL по следующим параметрам:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/testdb
spring.datasource.username=postgres
spring.datasource.password=astondb
```
## Отправка запросов через Postman
### Путь до эндпоинта:
````
http://localhost:8080/api/users
````

### Получить всех пользователей
**GET** `/api/users`

#### Пример запроса
GET http://localhost:8080/api/users

### Получить пользователя по ID
**GET** `/api/users/{id}`

#### Пример запроса
GET http://localhost:8080/api/users/1

### Создать пользователя
**POST** `/api/users`

#### Пример запроса
POST http://localhost:8080/api/users

#### Тело запроса
````
{
  "name": "Тест",
  "email": "test@example.com"
}
````
### Обновить пользователя
**PUT** `/api/users/{id}`

#### Пример запроса
PUT http://localhost:8080/api/users/1

#### Тело запроса
````
{
  "name": "Тест",
  "email": "test@example.com"
}
````
### Удалить пользователя
**DELETE** `/api/users/{id}`

#### Пример запроса
DELETE http://localhost:8080/api/users/1
