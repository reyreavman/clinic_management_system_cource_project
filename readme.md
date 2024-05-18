# course-project
## Инфраструктура
### Keycloak

В проекте используется как OAuth 2.0/OIDC-сервер для авторизации сервисов и аутентификации пользователей.

Запуск в Docker:

```shell
docker run --name clinic-keycloak -p 8083:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -v ./config/keycloak/import:/opt/keycloak/data/import quay.io/keycloak/keycloak:23.0.7 start-dev --import-realm
```

### PostgreSQL

В проекте используется в качестве БД модуля клиники.

Запуск в Docker:

```shell
docker run --name clinic-db -p 5445:5432 -e POSTGRES_USER=clinic -e POSTGRES_PASSWORD=clinic -e POSTGRES_DB=clinic postgres:16
```
