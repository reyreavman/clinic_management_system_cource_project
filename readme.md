# Сourse-project
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

### VictoriaMetrics

В проекте используется для сбора и хранения метрик сервисов.

Запуск в Docker:

```shell
docker run --name clinic-metrics -p 8428:8428 -v ./config/victoria-metrics/promscrape.yaml:/promscrape.yaml victoriametrics/victoria-metrics:v1.93.12 -promscrape.config=promscrape.yaml
```

### Grafana

В проекте используется в качестве системы визуализации метрик, логов и трассировок.

```shell
docker run --name clinic-grafana -p 3000:3000 -v ./data/grafan:/var/lib/grafana -u "$(id -u)" grafana/grafana:10.2.4
```

### Grafana Loki

В проекте используется в качестве системы централизованной сборки и хранилища логов.

```shell
docker run --name clinic-loki -p 3100:3100 grafana/loki:2.9.4
```

### Grafana Tempo

В проекте используется в качестве централизованного хранилища трассировок.

Запуск в Docker:

```shell
docker run --name selmag-tracing -p 3200:3200 -p 9095:9095 -p 4317:4317 -p 4318:4318 -p 9411:9411 -p 14268:14268 -v ./config/tempo/tempo.yaml:/etc/tempo.yaml grafana/tempo:2.3.1 -config.file=/etc/tempo.yaml
```

### Spring Eureka Server

В проекте используется в качестве модуля service discovery и балансировки нагрузки

### Spring API Gateway

В проекте используется в качестве модуля, реализующего паттерн API Gateway