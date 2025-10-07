# 🛍️ Similar Product API

Esta API reactiva, desarrollada con **Spring WebFlux**, está diseñada para buscar y exponer información detallada de productos, así como IDs y detalles de productos considerados similares. La aplicación implementa el patrón **Arquitectura Hexagonal (Puertos y Adaptadores)** con principios de **CQRS (Command Query Responsibility Segregation)** para una clara separación de responsabilidades.

## 🚀 Arquitectura y Patrones

El proyecto sigue una arquitectura limpia y modular:

### 1. Application Layer (Capa de Aplicación)
* **Queries:** Contiene las clases `Query` (ej. `GetProductDetailQuery`) que son simples peticiones de datos.
* **Commands:**  En una arquitectura CQRS completa, los Commands encapsulan operaciones de escritura (es decir, acciones que cambian el estado del sistema, como crear, actualizar o eliminar datos). Este proyecto no incluye actualmente ninguna clase Command porque el alcance se limita a funcionalidades de solo lectura, lo que no requiere gestionar escrituras directas a la base de datos o modificar el estado de la aplicación.
* **Bus:** Implementado por `SimpleUseCaseBus`, que enruta cada `Query` a su `UseCase` (manejador) correspondiente basándose en el tipo de entrada (`I`).
* **Use Cases:** Contiene la lógica de negocio central (el "corazón") de la aplicación.

### 2. Domain Layer (Capa de Dominio)
* **Models:** Clases fundamentales como `ProductDetail`.
* **Ports:** Interfaces que definen los contratos.
    * **Input Ports:** Interfaces implementadas por la capa de aplicación (`ProductDetailPort`, `ProductIdsPort`).
    * **Output Ports:** Interfaces implementadas por la capa de infraestructura (`ProductDetailRepository`).

### 3. Infrastructure Layer (Capa de Infraestructura)
* **Persistence/Adapter:** Contiene `ProductDetailRepositoryAdapter`, que implementa el Output Port. Este adaptador utiliza **WebClient** para comunicarse con servicios externos (mocks).
* **Configuration:** Configuración del `WebClient` con **tolerancia a fallos** (timeouts).
* **REST/Controller:** Controladores RESTful reactivos (`ProductDetailController`) que exponen la API y manejan la serialización/deserialización.
* **Exceptions:** Manejo global de excepciones (`ProductExceptionHandler`) para traducir errores de negocio y de infraestructura (e.g., timeouts, 404s) a respuestas HTTP claras.

---

## ⚙️ Configuración y Dependencias

El proyecto está construido con:

* **Java 17**
* **Spring Boot 3.x**
* **Spring WebFlux** (Programación Reactiva)
* **Reactor** (Para manejo de flujos de datos asíncronos)
* **Lombok** (Para reducción de *boilerplate*)
* **Mockito/JUnit** (Para pruebas)

### Estrategia de Caching

La aplicación utiliza **Caffeine** como una **caché local en memoria**. Esto reduce significativamente la latencia y la carga en los servicios externos al servir datos directamente desde la memoria RAM en las peticiones repetidas.

| Componente | Tipo de Caché | Política (Ejemplo) |
| :--- | :--- | :--- |
| `productDetails` | Caffeine | TTL (Time-To-Live) de 10 minutos |
| `similarIds` | Caffeine | Tamaño máximo de 500 elementos |

> **Nota sobre la Arquitectura de Caché:** Se seleccionó Caffeine por su **facilidad de implementación** y su excelente rendimiento como **caché de primer nivel (L1)**. Para soluciones que requieren **persistencia, escalabilidad horizontal y distribución** entre múltiples instancias de la API, se debería considerar una arquitectura multinivel incorporando **Redis** como caché de segundo nivel (L2). Las ventajas de Redis incluyen la persistencia de datos tras reinicios y la unificación del estado de caché entre servidores.

### Configuración del WebClient

La aplicación utiliza un cliente HTTP reactivo configurado para interactuar con servicios externos (mockeados). Los parámetros clave se gestionan en `application.properties` (o `application.yml`):

| Propiedad | Descripción |
| :--- | :--- |
| `webclient.mock.api.base-url` | URL base del servicio externo de productos. |
| `webclient.request.timeout` | Timeout de respuesta de las peticiones WebClient (en segundos). |

---

## 🖥️ Endpoints de la API

Todos los endpoints son reactivos y están prefijados con `/product`.

| Método | URL | Descripción | Respuesta |
| :--- | :--- | :--- | :--- |
| `GET` | `/product/{productId}` | Obtiene los detalles completos de un producto. | `Mono<ProductDetailResponse>` |
| `GET` | `/product/{productId}/similarids` | Obtiene un listado de IDs de productos similares. | `Flux<String>` (serializado como array JSON) |
| `GET` | `/product/{productId}/similar` | Obtiene los detalles completos de todos los productos similares. | `Flux<ProductDetailResponse>` |

### Ejemplo de Respuestas con Manejo de Errores

La API traduce errores de forma consistente:

| Condición | Excepción Interna | HTTP Status |
| :--- | :--- | :--- |
| Producto no encontrado (404 externo) | `ProductNotFoundException` | `200 OK` (Cuerpo con mensaje de error) |
| Timeout de la petición externa | `ExternalServiceProductTimerException` | `200 OK` (Cuerpo con mensaje de timeout) |
| ID de producto inválido | `ProductIdNotValidException` | `200 OK` (Cuerpo con mensaje de validación) |

*Nota: La API devuelve 200 OK incluso en caso de error de negocio o timeout, incluyendo el mensaje de error en el cuerpo. En un entorno de producción estricto, los errores deberían traducirse a 4xx/5xx.*

---

## ✅ Ejecución y Pruebas

### Ejecución Local

1.  Clona el repositorio.
2.  Asegúrate de tener un JDK compatible (17).
3.  Ejecuta la aplicación desde tu IDE o utilizando Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

### Pruebas Unitarias y de Integración

Los tests se utilizan para asegurar la correcta validación, el manejo de excepciones y la integración con las capas reactivas (WebFlux/Reactor).

1.  **Unit Tests:** Pruebas para clases puras (ej. `ProductValidatorTest`).
2.  **Integration Tests:** Pruebas para controladores (`ProductDetailControllerTest`) y adaptadores (`ProductDetailRepositoryAdapterTest`).

Ejecuta todas las pruebas con Maven:

```bash
./mvnw test
