# CloseRent - Sistema de Alquiler de Prendas

Sistema de gestión de alquiler de prendas (vestidos de dama, trajes de caballero y disfraces) desarrollado con **Spring Boot 4.0.3** y **patrones de diseño**.

## Descripción del Negocio

CloseRent es un negocio de alquiler de vestidos para dama, caballero y disfraces. El sistema permite:

- Registrar y gestionar prendas, clientes y empleados
- Crear servicios de alquiler con validación de disponibilidad
- Gestionar cola de lavandería con sistema de prioridades
- Consultar servicios por cliente, fecha o número
- Consultar prendas por talla

## Patrones de Diseño Implementados

### 1. **Factory Method** (Creación de Prendas)
- **Ubicación**: `com.example.closetrent.model.factory`
- **Clases**:
  - `PrendaFactory` (interfaz)
  - `VestidoDamaFactory`
  - `TrajeCaballeroFactory`
  - `DisfrazFactory`
- **Propósito**: Encapsular la creación de diferentes tipos de prendas con sus atributos específicos
- **Uso**: En `PrendaController` para crear prendas según el tipo seleccionado

### 2. **Decorator** (Prioridad de Lavado)
- **Ubicación**: `com.example.closetrent.model.decorator`
- **Clases**:
  - `IPrendaDecorator` (interfaz)
  - `PrendaDecorator` (clase abstracta)
  - `PrendaBaseDecorator` (sin prioridad)
  - `PrendaPrioridad` (con prioridad)
- **Propósito**: Agregar dinámicamente funcionalidad de prioridad a las prendas para el sistema de lavandería
- **Uso**: En `LavanderiaService` para gestionar la cola con prioridades

### 3. **Composite** (Colecciones de Prendas)
- **Ubicación**: `com.example.closetrent.model.composite`
- **Clases**:
  - `IPrendaComponent` (interfaz)
  - `PrendaCollections` (contenedor)
  - `PrendaIndividual` (hoja)
- **Propósito**: Tratar objetos individuales y composiciones de manera uniforme
- **Uso**: Permite agrupar prendas jerárquicamente

### 4. **Singleton + Facade** (Negocio de Alquiler)
- **Ubicación**: `com.example.closetrent.service`
- **Clase**: `NegocioAlquiler`
- **Propósito**:
  - **Singleton**: Garantizar una única instancia del servicio principal
  - **Facade**: Proporcionar una interfaz simplificada para todas las operaciones del negocio
- **Uso**: Spring Boot garantiza el patrón Singleton con `@Service`, y la clase actúa como fachada para acceder a todos los repositorios y servicios

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 4.0.3**
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria (desarrollo)
- **MariaDB** - Base de datos producción (configuración disponible)
- **Thymeleaf** - Motor de plantillas
- **Lombok** - Reducción de código boilerplate
- **Bootstrap 5** - Interfaz de usuario

## Requisitos

- JDK 17 o superior
- Maven 3.6+ (incluido Maven Wrapper)
- MariaDB (opcional, para producción)

## Instalación y Ejecución

### 1. Configurar JAVA_HOME (Windows)

```bash
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
```

### 2. Compilar el proyecto

```bash
./mvnw clean compile
```

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### 4. Acceder a la consola H2 (desarrollo)

URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:atuendosdb`
- Usuario: `sa`
- Contraseña: (vacío)

## Configuración de Base de Datos

### H2 (Desarrollo - por defecto)
Ya está configurado en `application.properties`

### MariaDB (Producción)
1. Crear la base de datos:
```sql
CREATE DATABASE close_rent_bd;
```

2. Descomentar en `application.properties`:
```properties
spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/close_rent_bd
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

3. Comentar la configuración de H2

## Funcionalidades

### 1. Gestión de Clientes
- Registrar nuevo cliente
- Listar todos los clientes
- Consultar servicios vigentes por cliente

### 2. Gestión de Empleados
- Registrar nuevo empleado con cargo
- Listar todos los empleados

### 3. Gestión de Prendas
- Registrar prendas usando **Factory Method**:
  - Vestidos de dama (con pedrería, largo/corto, piezas)
  - Trajes de caballero (tipo, accesorio de cuello)
  - Disfraces (con nombre)
- Listar todas las prendas
- Consultar prendas por talla

### 4. Servicios de Alquiler
- Registrar nuevo servicio con:
  - Validación de cliente y empleado existentes
  - Validación de disponibilidad de prendas
  - Generación automática de número de servicio
  - Captura automática de fecha de solicitud
- Consultar servicio por número
- Consultar servicios por cliente (solo vigentes)
- Consultar servicios por fecha de alquiler

### 5. Gestión de Lavandería
- Registrar prenda para lavandería usando **Decorator**:
  - Sin prioridad (orden de llegada)
  - Con prioridad (manchada, delicada, administrativa)
- Visualizar listado ordenado (prioridad primero)
- Enviar lote de prendas a lavandería

## Estructura del Proyecto

```
src/main/java/com/example/closetrent/
├── model/
│   ├── Persona.java (clase abstracta)
│   ├── Cliente.java
│   ├── Empleado.java
│   ├── Prenda.java (clase abstracta)
│   ├── VestidoDama.java
│   ├── TrajeCaballero.java
│   ├── Disfraz.java
│   ├── ServicioAlquiler.java
│   ├── factory/           # Factory Method Pattern
│   ├── decorator/         # Decorator Pattern
│   └── composite/         # Composite Pattern
├── repository/
│   ├── ClienteRepository.java
│   ├── EmpleadoRepository.java
│   ├── PrendaRepository.java
│   └── ServicioAlquilerRepository.java
├── service/
│   ├── NegocioAlquiler.java    # Singleton + Facade
│   └── LavanderiaService.java
├── controller/
│   ├── HomeController.java
│   ├── ClienteController.java
│   ├── EmpleadoController.java
│   ├── PrendaController.java
│   ├── ServicioAlquilerController.java
│   └── LavanderiaController.java
└── ClosetRentApplication.java
```

## Pruebas

Para ejecutar las pruebas:

```bash
./mvnw test
```

## Documentación Técnica

### Herencia de Entidades JPA
Se utiliza la estrategia `SINGLE_TABLE` para:
- **Persona** → Cliente, Empleado
- **Prenda** → VestidoDama, TrajeCaballero, Disfraz

### Relaciones
- `ServicioAlquiler` ← Many-to-One → `Cliente`
- `ServicioAlquiler` ← Many-to-One → `Empleado`
- `ServicioAlquiler` ← Many-to-Many → `Prenda`

### Validaciones
- Cliente y Empleado deben estar registrados antes de crear un servicio
- Las prendas deben estar disponibles para la fecha solicitada
- No se permite duplicar servicios para la misma prenda en la misma fecha

## Autor

Desarrollado como proyecto académico aplicando patrones de diseño.

## Licencia

Este proyecto es de uso académico.
