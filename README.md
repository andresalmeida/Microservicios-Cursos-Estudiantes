# Microservicios de Gestión de Cursos y Estudiantes 🤓☝🏻

Este proyecto se basa en un sistema de microservicios desarrollado con Spring Boot, que gestiona los cursos y los estudiantes, permitiendo realizar operaciones CRUD sobre ambos. El sistema está diseñado para ser desplegado usando Docker y se conecta a una base de datos MySQL.

## Tecnologías Utilizadas 

- **Spring Boot** para el desarrollo de los microservicios.
- **Docker** para la creación y gestión de contenedores.
- **MySQL** como sistema de gestión de bases de datos.
- **Maven** como herramienta de construcción de proyectos.
- **Postman** para probar las API.

## Estructura del Proyecto

El proyecto está dividido en dos microservicios:

- **micro-cursos**: Microservicio encargado de gestionar los cursos.
- **micro-estudiantes**: Microservicio encargado de gestionar a los estudiantes.

Cada microservicio está configurado con su propio puerto y se conecta a la misma base de datos MySQL.

### Estructura de Carpetas
```bash
Microservicios Chidos/
├── micro-cursos/
│   ├── src/
│   ├── Dockerfile
│   ├── pom.xml
│   └── application.properties
│
├── micro-estudiantes/
│   ├── src/
│   ├── Dockerfile
│   ├── pom.xml
│   └── application.properties
│
└── docker-compose.yml
```
## Configuración

### application.properties (Microservicios)

Ambos microservicios comparten una configuración similar en sus archivos `application.properties`, donde se define la URL de conexión a la base de datos, el puerto del servidor y la configuración de Hibernate/JPA.

#### Ejemplo de configuración para el microservicio de **micro-cursos**:

```properties
spring.application.name=micro-cursos
server.port=8003
spring.datasource.url=jdbc:mysql://localhost:3307/sisdb2025
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
logging.level.org.hibernate.SQL=debug
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=trace
```
### Dockerfile (Para ambos microservicios)

Cada microservicio tiene un `Dockerfile` para crear su contenedor, que contiene partes importantes como:

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8003
ADD target/micro-cursos-0.0.1-SNAPSHOT.jar micro-cursos.jar
ENTRYPOINT ["java", "-jar", "micro-cursos.jar"]
```
### Docker Compose (General)

De igual manera, se tiene un archivo `docker-compose.yml` que configura los servicios y cómo deben ejecutarse en contenedores Docker

## Instrucciones para el Despliegue

1. Construir el microservicio
   
   - Navegar a cada carpeta (`micro-cursos` y `micro-estudiantes`).
   - Ejecutar `mvn clean install` para construir el archivo JAR.
     
2. Construir y ejecutar los contenedores

   - Desde la raiz del proyecto, donde está el archivo `docker-compose.yml`, ejecutar el siguiente comando para construir y ejecutar los contenedores:

     ```bash
     docker-compose up --build
     ```
3. Verificar el funcionamiento
   
   - Asegúrate de que los microservicios están corriendo en los puertos configurados (8003 para micro-cursos y 8002 para micro-estudiantes).
   - Puedes probar las APIs usando Postman o cualquier otra herramienta similar.

4. Acceder a las APIs:

   - Los endpoints estarán disponibles en:
     - `http://localhost:8003/api/cursos` para el microservicio de micro-cursos.
     - `http://localhost:8002/api/estudiantes` para el microservicio de micro-estudiantes.
   
## Solución de problemas

- Si hay problemas de conexión a la base de datos, asegúrate de que MySQL está corriendo correctamente en Docker y que las credenciales en los archivos application.properties coinciden con los valores de la base de datos en Docker.
- Si algún microservicio no se está ejecutando correctamente, verifica los logs del contenedor usando el siguiente comando:
  
  ```bash
  docker logs <nombre_del_contenedor>
  ```
## Novedades encontradas

- Asegúrate de que en el archivo `application.properties` no existan caracteres como una "ñ" ni tildes, pues dará error al momento de construir el JAR.
- Si al momento de correr el `docker-compose up --build` los contenedores de los microservicios no están corriendo, es porque debes crear la base de datos establecida en el `docker-compose.yml` puedes hacerlo:

  ```bash
  docker exec -it mysql-micro-spring bash
  ```
- Nosotros usamos el nombre `mysql-micro-spring` porque es el nombre que le dimos a la BD en Docker, y está mapeada en el puerto 3307 para no causar conflictos con el 3306 que usa MySQL por defecto en local
- Dentro del bash, cuando accedas, usa:
  
  ```bash
  mysql -u root -p
  ```
- Luego te pedirá la contraseña (que es `rootpassword` o la que establezvas según tu configuración en `docker-compose.yml`).
- ¡Listo! Ahora deberías estar dentro del contenedor de MySQL y poder hacer consultas directamente en la base de datos o verificar su estado.

## Contribución

Este proyecto está abierto para contribuciones. Si tienes alguna mejora, sugerencia o corrección, siéntete libre de abrir un pull request.
