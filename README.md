# Proyecto Literalura - API de Libros

**Literalura** es una aplicación backend desarrollada en Java utilizando **Spring Boot** que consume la API de **Gutendex** para obtener libros y guardarlos en una base de datos PostgreSQL. La aplicación permite a los usuarios realizar búsquedas y gestionar libros y autores, basados en los datos de la API, a través de un menú interactivo.

## Requisitos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes programas:

- **Java 17** o superior
- **Maven** (para la gestión de dependencias y construcción del proyecto)
- **PostgreSQL** (base de datos para almacenar los libros y autores)

## Instalación

1. Clona este repositorio en tu máquina local:

    ```bash
    git clone https://github.com/tu_usuario/literalura.git
    ```

2. Navega al directorio del proyecto:

    ```bash
    cd literalura
    ```

3. Asegúrate de tener configurada tu base de datos PostgreSQL. Crea una base de datos llamada `literalura` o cambia la configuración de la base de datos en el archivo `application.properties` si lo prefieres.

4. Configura las credenciales de tu base de datos en el archivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    ```

5. Compila el proyecto con Maven:

    ```bash
    mvn clean install
    ```

6. Ejecuta el proyecto:

    ```bash
    mvn spring-boot:run
    ```

## Uso

Una vez que el proyecto esté en funcionamiento, verás un menú interactivo en la consola donde podrás elegir entre las siguientes opciones:

1. **Buscar libros por título**: Permite buscar libros usando su título.
2. **Listar libros registrados**: Muestra todos los libros que están registrados en la base de datos.
3. **Listar autores registrados**: Muestra todos los autores que tienen libros registrados en la base de datos.
4. **Listar autores vivos en un determinado año**: Permite consultar qué autores estaban vivos en un año específico.
5. **Listar libros por idioma**: Permite filtrar los libros por su idioma (español, inglés, francés, portugués).

### Ejemplo de Interacción:

```text
************************************************
*****       BIENVENIDO A LITERALURA       ******
************************************************

Elige la opción a través de su número

1 - Buscar libros por titulo
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado ano
5 - Listar libros por idioma

0 - Salir

************************************************
