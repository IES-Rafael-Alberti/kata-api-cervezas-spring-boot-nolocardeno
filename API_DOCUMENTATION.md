# API de Cervezas - Documentación

## Descripción General

Esta API REST permite gestionar información sobre cervezas, cerveceras, categorías y estilos de cerveza. Está desarrollada con Spring Boot 3.2 y utiliza MariaDB como base de datos.

## Configuración

### Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- Docker y Docker Compose (para la base de datos)

### Iniciar la Base de Datos

```bash
docker-compose up -d
```

Esto iniciará:
- **MariaDB**: Puerto 3306
- **Adminer** (gestor de BD): Puerto 8888

### Iniciar la Aplicación

```bash
cd api-cervezas
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

### Swagger UI

Accede a la documentación interactiva de Swagger en:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

---

## Endpoints

### 🍺 Cervezas (Beers)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/beers` | Listar todas las cervezas |
| GET | `/beers/paginated` | Listar cervezas paginadas |
| HEAD | `/beers` | Obtener el conteo total de cervezas |
| GET | `/beer/{id}` | Obtener cerveza por ID |
| POST | `/beer` | Crear nueva cerveza |
| PUT | `/beer/{id}` | Actualizar cerveza completamente |
| PATCH | `/beer/{id}` | Actualizar cerveza parcialmente |
| DELETE | `/beer/{id}` | Eliminar cerveza |
| POST | `/beer/{id}/image` | Subir imagen de cerveza |
| GET | `/beer/{id}/image` | Obtener imagen de cerveza |

### 🏭 Cerveceras (Breweries)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/breweries` | Listar todas las cerveceras |
| GET | `/breweries/paginated` | Listar cerveceras paginadas |
| HEAD | `/breweries` | Obtener el conteo total de cerveceras |
| GET | `/brewerie/{id}` | Obtener cervecera por ID |

### 📁 Categorías (Categories)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/categories` | Listar todas las categorías |
| GET | `/categories/paginated` | Listar categorías paginadas |
| HEAD | `/categories` | Obtener el conteo total de categorías |
| GET | `/categorie/{id}` | Obtener categoría por ID |

### 🎨 Estilos (Styles)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/styles` | Listar todos los estilos |
| GET | `/styles/paginated` | Listar estilos paginados |
| HEAD | `/styles` | Obtener el conteo total de estilos |
| GET | `/style/{id}` | Obtener estilo por ID |

---

## Ejemplos de Uso

### Listar todas las cervezas

**Request:**
```http
GET http://localhost:8080/beers
```

**Response:**
```json
[
  {
    "id": 1,
    "breweryId": 812,
    "name": "Hocus Pocus",
    "catId": 11,
    "styleId": 116,
    "abv": 4.5,
    "ibu": 0,
    "srm": 0,
    "upc": 0,
    "filepath": "",
    "descript": "Our take on a classic Belgian style..."
  }
]
```

### Obtener cerveza por ID

**Request:**
```http
GET http://localhost:8080/beer/1
```

**Response:**
```json
{
  "id": 1,
  "breweryId": 812,
  "name": "Hocus Pocus",
  "catId": 11,
  "styleId": 116,
  "abv": 4.5,
  "ibu": 0,
  "srm": 0,
  "upc": 0,
  "filepath": "",
  "descript": "Our take on a classic Belgian style..."
}
```

### Crear nueva cerveza

**Request:**
```http
POST http://localhost:8080/beer
Content-Type: application/json

{
  "name": "Mi Nueva Cerveza",
  "breweryId": 1,
  "catId": 1,
  "styleId": 1,
  "abv": 5.5,
  "ibu": 35,
  "srm": 12,
  "descript": "Una cerveza artesanal deliciosa"
}
```

**Response (201 Created):**
```json
{
  "id": 5916,
  "breweryId": 1,
  "name": "Mi Nueva Cerveza",
  "catId": 1,
  "styleId": 1,
  "abv": 5.5,
  "ibu": 35.0,
  "srm": 12.0,
  "upc": 0,
  "filepath": "",
  "descript": "Una cerveza artesanal deliciosa"
}
```

### Actualizar cerveza completamente (PUT)

**Request:**
```http
PUT http://localhost:8080/beer/5916
Content-Type: application/json

{
  "name": "Mi Cerveza Actualizada",
  "breweryId": 1,
  "catId": 2,
  "styleId": 3,
  "abv": 6.0,
  "ibu": 40,
  "srm": 15,
  "descript": "Descripción actualizada"
}
```

### Actualizar cerveza parcialmente (PATCH)

**Request:**
```http
PATCH http://localhost:8080/beer/5916
Content-Type: application/json

{
  "abv": 7.0,
  "descript": "Solo actualizo estos campos"
}
```

### Eliminar cerveza

**Request:**
```http
DELETE http://localhost:8080/beer/5916
```

**Response:** `204 No Content`

### Paginación

**Request:**
```http
GET http://localhost:8080/beers/paginated?page=0&size=10&sort=name,asc
```

**Parámetros de paginación:**
- `page`: Número de página (empieza en 0)
- `size`: Cantidad de elementos por página
- `sort`: Campo de ordenación y dirección (ej: `name,asc` o `id,desc`)

### Obtener conteo con HEAD

**Request:**
```http
HEAD http://localhost:8080/beers
```

**Response Headers:**
```
X-Total-Count: 5914
```

### Subir imagen de cerveza

**Request:**
```http
POST http://localhost:8080/beer/1/image
Content-Type: multipart/form-data

file: [archivo de imagen]
```

---

## Códigos de Respuesta HTTP

| Código | Descripción |
|--------|-------------|
| 200 | Operación exitosa |
| 201 | Recurso creado correctamente |
| 204 | Recurso eliminado correctamente |
| 400 | Datos de entrada inválidos |
| 404 | Recurso no encontrado |
| 500 | Error interno del servidor |

---

## Estructura del Proyecto

```
api-cervezas/
├── src/
│   └── main/
│       ├── java/com/kata/cervezas/
│       │   ├── ApiCervezasApplication.java
│       │   ├── config/
│       │   │   ├── OpenApiConfig.java
│       │   │   └── WebConfig.java
│       │   ├── controller/
│       │   │   ├── BeerController.java
│       │   │   ├── BreweryController.java
│       │   │   ├── CategoryController.java
│       │   │   └── StyleController.java
│       │   ├── dto/
│       │   │   ├── BeerDTO.java
│       │   │   ├── BeerCreateDTO.java
│       │   │   ├── BeerUpdateDTO.java
│       │   │   ├── BreweryDTO.java
│       │   │   ├── CategoryDTO.java
│       │   │   └── StyleDTO.java
│       │   ├── entity/
│       │   │   ├── Beer.java
│       │   │   ├── Brewery.java
│       │   │   ├── Category.java
│       │   │   └── Style.java
│       │   ├── exception/
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── ResourceNotFoundException.java
│       │   ├── repository/
│       │   │   ├── BeerRepository.java
│       │   │   ├── BreweryRepository.java
│       │   │   ├── CategoryRepository.java
│       │   │   └── StyleRepository.java
│       │   └── service/
│       │       ├── BeerService.java
│       │       ├── BreweryService.java
│       │       ├── CategoryService.java
│       │       ├── FileStorageService.java
│       │       └── StyleService.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## Modelo de Datos

### Beer (Cerveza)
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Integer | Identificador único |
| brewery_id | Integer | ID de la cervecera |
| name | String | Nombre de la cerveza |
| cat_id | Integer | ID de la categoría |
| style_id | Integer | ID del estilo |
| abv | Float | Alcohol por volumen (%) |
| ibu | Float | Unidades Internacionales de Amargor |
| srm | Float | Método de Referencia Estándar (color) |
| upc | Long | Código de barras |
| filepath | String | Ruta de la imagen |
| descript | Text | Descripción |

### Brewery (Cervecera)
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Integer | Identificador único |
| name | String | Nombre de la cervecera |
| address1 | String | Dirección línea 1 |
| address2 | String | Dirección línea 2 |
| city | String | Ciudad |
| state | String | Estado/Provincia |
| code | String | Código postal |
| country | String | País |
| phone | String | Teléfono |
| website | String | Sitio web |
| descript | Text | Descripción |

### Category (Categoría)
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Integer | Identificador único |
| cat_name | String | Nombre de la categoría |

### Style (Estilo)
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Integer | Identificador único |
| cat_id | Integer | ID de la categoría |
| style_name | String | Nombre del estilo |

---

## Autor

Desarrollado como parte de la Kata de Spring Boot para el módulo de Desarrollo en Servidor.
