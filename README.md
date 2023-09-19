# retoDevsu API REST

## Introducción

Este proyecto es una API REST desarrollada en Java utilizando intellij-java-google-style.xml para el
formato del código, también usando Spring Boot. Ésta API permite gestionar movimientos
bancarios, realizar operaciones relacionadas con clientes, cuentas y movimientos, además de generar
informes de movimientos por cliente dentro de un rango de fechas.

## Tecnologías Utilizadas

* Java 17
* Spring Boot 3.1.3
* Maven
* Lombok
* Postgresql
* Docker

## Descripción

El proyecto "retoDevsu API REST" representa una API diseñada como parte de una prueba técnica para
Devsu. Su objetivo principal es permitir la realización de diversas operaciones relacionadas con
transacciones bancarias, incluyendo la creación, consulta, actualización y eliminación de clientes y
cuentas bancarias. Además, ofrece la capacidad de efectuar transacciones de depósito y retiro en
cuentas, con restricciones en los montos de retiro diarios. También incluye la funcionalidad para
generar informes de movimientos bancarios de clientes dentro de un intervalo de fechas determinado.

## Configuración inicial del proyecto

## 1. Configurar Base de datos en entorno docker.

En la raiz del proyecto se encuentra un archivo docker-compose.yml que levanta una base de datos"
dbdevsu" en postgres en el puerto 5432.

```
 ./docker-compose.yml
```

Estando en la raíz del proyecto se procederá a abrir terminal y ejecutar el comando

```
docker compose up
```

si solicita algún permiso se le debera anteponer la palabra sudo para darle permisos de
administrador.

```
sudo docker compose up
```

Esperar a que termine de levantar el ambiente docker

## 2. Ejecutar proyecto.

Despues de debera ejecutar el proyecto esta api esta corriendo en el puerto 8080.

## 3. Api disponible

* Data Rest: http://localhost:8080

## 4. Postman

En la raíz del proyecto se encuentra el archivo.

```
./Coleccion.postman_collection.json
```

Este json se deberá importar en postman para poder probar los endpoints.

## Endpoints

La API proporciona varios endpoints para acceder a estas funcionalidades. Entre los principales
endpoints se encuentran:

* /cuentas: Permite gestionar cuentas bancarias, incluyendo la creación de nuevas cuentas, consulta
  de
  información detallada de cuentas específicas, actualización de datos de cuentas existentes y
  eliminación de cuentas.

* /clientes: Ofrece operaciones relacionadas con clientes, como la creación de nuevos clientes,
  consulta detallada de información de clientes específicos, actualización de detalles de clientes
  existentes y eliminación de clientes.

* /movimientos: Facilita la administración de movimientos bancarios, permitiendo el registro de
  nuevos
  movimientos de depósito y retiro en cuentas específicas.

* /reportes: Permite generar informes de movimientos bancarios por cliente en un rango de fechas
  específico.

En el archivo "Coleccion.postman_collection.json" se proporciona una colección que puede ser
importada
en Postman para validar los endpoints de la API.

## Tecnologías y Dependencias Principales

Este proyecto se basa en diversas tecnologías y dependencias fundamentales:

* Lenguaje de Programación: El proyecto está desarrollado en Java 17, que es un lenguaje de
  programación de alto rendimiento y versátil.

* Gestión de Dependencias: Utiliza Apache Maven como herramienta para gestionar las dependencias y
  construir el proyecto.

* Framework de Desarrollo: Se ha empleado Spring Boot 3.1.0, un framework que simplifica el
  desarrollo de aplicaciones web y proporciona un entorno robusto.

* Persistencia de Datos: Para la capa de persistencia de datos, se ha empleado JPA (Java Persistence
  API), que facilita el acceso a bases de datos relacionales.

* Base de Datos: La aplicación se conecta a una base de datos PostgreSQL durante la ejecución.

* Lombok: Lombok se utiliza para reducir la cantidad de código repetitivo y simplificar la creación
  de clases y métodos.

* Contenedor Docker: El proyecto está configurado para ejecutarse en un entorno Docker, lo que
  facilita la creación de una imagen de Docker con todas las dependencias necesarias para su
  ejecución.

* Docker Compose: Se proporciona un archivo docker-compose.yml que permite la creación de un
  contenedor Docker con una base de datos PostgreSQL.

Esta combinación de tecnologías y dependencias crea un entorno de desarrollo sólido y eficiente para
la construcción y ejecución de la aplicación.





