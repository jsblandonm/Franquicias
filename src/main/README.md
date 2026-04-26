# Franchise API 🏪

REST API para la gestión de franquicias, sucursales y productos,
desarrollada con Spring Boot, programación reactiva (WebFlux) y MongoDB.

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3
- Spring WebFlux (programación reactiva)
- MongoDB
- Docker & Docker Compose
- Maven

## 📋 Prerrequisitos

- Java 17+
- Maven 3.8+
- Docker y Docker Compose

## 🚀 Cómo ejecutar el proyecto

### Opción 1 — Docker Compose (recomendada)

Clona el repositorio:
```bash
git clone https://github.com/jsblandonm/franchise-api.git
cd franchise-api
```

Levanta la aplicación completa:
```bash
docker-compose up --build
```

La API estará disponible en: `http://localhost:8080`

---

### Opción 2 — Local con Maven

Primero levanta MongoDB con Docker:
```bash
docker-compose up -d mongo
```

Luego corre la aplicación:
```bash
mvn spring-boot:run
```

## 📡 Endpoints

### Franquicias
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/franchises` | Crear franquicia |
| PATCH | `/api/franchises/{id}/name?newName=` | Actualizar nombre |

### Sucursales
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/branches` | Crear sucursal |
| PATCH | `/api/branches/{id}/name?newName=` | Actualizar nombre |

### Productos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/products` | Crear producto |
| DELETE | `/api/products/{id}` | Eliminar producto |
| PATCH | `/api/products/{id}/stock?newStock=` | Actualizar stock |
| PATCH | `/api/products/{id}/name?newName=` | Actualizar nombre |
| GET | `/api/products/top-stock/franchise/{franchiseId}` | Producto con más stock por sucursal |

## 📦 Ejemplos de uso

### Crear franquicia
```json
POST "/api/franchises"
{
    "name": "McDonald's"
}
```

### Crear sucursal
```json
POST /api/branches
{
    "name": "Sucursal Centro",
    "franchiseId": "id_de_la_franquicia"
}
```

### Crear producto
```json
POST /api/products
{
    "name": "Big Mac",
    "stock": 100,
    "branchId": "id_de_la_sucursal"
}
```

### Producto con más stock por sucursal
```json
GET /api/products/top-stock/franchise/{franchiseId}

// Respuesta
[
    {
        "branchId": "abc123",
        "branchName": "Sucursal Centro",
        "productId": "xyz789",
        "productName": "Big Mac",
        "stock": 150
    }
]
```

## 🏗️ Arquitectura

El proyecto está estructurado siguiendo los principios de **Clean Architecture**:

domain/          → Modelos y contratos (sin dependencias externas)

application/     → Casos de uso y lógica de negocio

infrastructure/  → Controllers, repositorios y configuración

## 🧪 Pruebas

Corre los tests unitarios con:
```bash
mvn test
```

## 👤 Autor

Johan Sebastian Blandón Montoya
- LinkedIn: [jsblandónmontoya](https://www.linkedin.com/in/jsblandónmontoya)
- GitHub: [jsblandonm](https://github.com/jsblandonm)

