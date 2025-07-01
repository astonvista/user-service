# user-service
## Инструкция по запуску
### Создание контейнера PostgreSQL в Docker
После запуска Docker, запустите в терминале:
```
docker-compose up -d
```
PostgreSQL будет доступен на localhost:5432.
### Для корректной работы необходим запущенный notification-service.
## Отправка запросов через Postman
#### Путь до эндпоинта:
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
  "email": "test@example.com",
  "age": 18
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
  "email": "test@example.com",
  "age": 18
}
````
### Удалить пользователя
**DELETE** `/api/users/{id}`

#### Пример запроса
DELETE http://localhost:8080/api/users/1
