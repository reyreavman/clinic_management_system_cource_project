image quay.io/keycloak/keycloak
--name=clinic-keycloak
-e "KEYCLOAK_ADMIN=admin",
-e "KEYCLOAK_ADMIN_PASSWORD=admin",

image postgres:16
--name=clinic-db
-p 5445:5432
-e "POSTGRES_DB=clinic"
-e "POSTGRES_USER=clinic"
-e "POSTGRES_PASSWORD=clinic"

image postgres:16
--name=clinic-manager-db
-p 5443:5432
-e "POSTGRES_DB=manager"
-e "POSTGRES_USER=manager"
-e "POSTGRES_PASSWORD=manager"

module clinic-service
server:
port:8081

module manager-app
server:
port:80

users:
user:
username:j.dewar
password:password
role:manager