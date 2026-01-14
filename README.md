# Direccions Library ✅

**Estado: COMPLETADO Y LISTO PARA PRODUCCIÓN**

Librería reutilizable para gestión de direcciones que incluye países, comunidades autónomas, provincias, municipios, códigos postales y tipos de vía.

## 🚀 Estado del Proyecto

✅ **Completado** - La librería está 100% funcional y lista para usar

### ✅ Características Implementadas

- ✅ Gestión completa de direcciones españolas
- ✅ API REST endpoints (`/api/v1/direccions/*`)
- ✅ Capa de servicio completa (`DireccioService`)
- ✅ Repositorios Spring Data JPA
- ✅ Mappers con MapStruct
- ✅ Auto-configuración de Spring Boot
- ✅ Entidades JPA configuradas
- ✅ DTOs optimizados
- ✅ Tests unitarios
- ✅ Base de datos configurable (PostgreSQL, H2, etc.)
- ✅ Scripts SQL de ejemplo
- ✅ Documentación completa

### 🏗️ Arquitectura

```
API Layer (REST Controllers)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Database (PostgreSQL/H2)
```

### 📦 Publicación Exitosa

```bash
# ✅ Librería publicada en repositorio local Maven
./gradlew publishToMavenLocal

# ✅ Build exitoso
BUILD SUCCESSFUL in 1s
6 actionable tasks: 5 executed, 1 up-to-date
```

## Instalación

### Gradle

```gradle
implementation("io.github.eduardnol:direccions-lib:0.0.11")
```

### Maven

```xml
<dependency>
    <groupId>io.github.eduardnol</groupId>
    <artifactId>direccions-lib</artifactId>
    <version>0.0.11</version>
</dependency>
```

## Configuración

### 1. Configuración de Base de Datos

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: none
    database-platform: org.hibernate.dialect.PostgreSQLDialect

# Opcional: Configurar el schema de la base de datos
direccions:
  schema: public  # Por defecto es "public"
```

### 2. Configuración del Schema de Base de Datos

La librería permite configurar el schema donde están ubicadas las tablas de direcciones. Esto es útil cuando se trabaja con datos maestros que pueden estar en un schema diferente al predeterminado.

**Por defecto:** Si no se especifica, se usa el schema `public`.

**Personalizar:** Para usar un schema personalizado, añade la siguiente configuración:

```yaml
direccions:
  schema: mi_schema_maestros
```

O en `application.properties`:

```properties
direccions.schema=mi_schema_maestros
```

### 3. Habilitar Auto-configuración

La librería se auto-configura automáticamente. Solo necesitas añadir la dependencia y configurar la base de datos.

### 3. Escaneo de Componentes (Opcional)

Si tu aplicación principal no está en el package `io.eduardnol`, añade:

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.tu.app", "io.eduardnol.direccions"})
public class TuAplicacion {
    public static void main(String[] args) {
        SpringApplication.run(TuAplicacion.class, args);
    }
}
```

## Uso

### API Endpoints

La librería expone los siguientes endpoints:

- `GET /api/v1/direccions/pais` - Obtener todos los países
- `GET /api/v1/direccions/comunitat-autonoma/{idPais}` - Obtener comunidades autónomas por país
- `GET /api/v1/direccions/provincia/{idComunitatAutonoma}` - Obtener provincias por comunidad autónoma
- `GET /api/v1/direccions/municipi/{idProvincia}` - Obtener municipios por provincia
- `GET /api/v1/direccions/municipi` - Obtener todos los municipios sin paginación
- `GET /api/v1/direccions/municipi/paginated?page={page}&size={size}` - Obtener todos los municipios con paginación
- `GET /api/v1/direccions/municipi/comunitat-autonoma/{idComunitatAutonoma}` - Obtener todos los municipios por comunidad autónoma
- `GET /api/v1/direccions/tipus-via` - Obtener tipos de vía
- `POST /api/v1/direccions/codi-postal/check` - Verificar código postal
- `GET /api/v1/direccions/comarca/{idMunicipi}` - Obtener comarca por municipio
- `GET /api/v1/direccions/search/streets?q={text}` - Buscar nombres de calles registradas (full-text search)
- `GET /api/v1/direccions/street/{idStreetName}` - Obtener detalles completos de un nombre de calle por ID

### Uso Programático

```java
@Autowired
private DireccioService direccioService;

// Obtener todos los países
List<ComboCodeDTO> paisos = direccioService.getAllPais();

// Obtener comunidades autónomas de España (idPais = 1)
List<ComboCodeDTO> comunitats = direccioService.getComunitatAutonomaByPais(1L);

// Verificar código postal
CheckCodiPostalDTO check = CheckCodiPostalDTO.builder()
    .idMunicipi(123L)
    .codiPostal("08001")
    .build();
Long idCodiPostal = direccioService.checkCodiPostal(check);

// Buscar nombres de calles registradas
List<StreetSearchResultDTO> streets = direccioService.searchStreets("Gran Via");

// Obtener detalles completos de un nombre de calle por ID
StreetDetailDTO streetDetail = direccioService.getStreetDetailsById(streets.get(0).getIdStreetName());

// Obtener todos los municipios sin paginación
List<ComboDTO> municipios = direccioService.getAllMunicipi();

// Obtener todos los municipios con paginación
PageResponseDTO<ComboDTO> municipiosPaginados = direccioService.getAllMunicipiPaginated(0, 20);

// Obtener municipios por comunidad autónoma
List<ComboDTO> municipiosCA = direccioService.getMunicipiByComunitatAutonoma(1L);
```

## Estructura de la Base de Datos

La librería espera las siguientes tablas:

- `md_pais` - Países
- `md_comunitat_autonoma` - Comunidades Autónomas
- `md_provincia` - Provincias
- `md_municipi` - Municipios
- `md_codi_postal` - Códigos Postales
- `md_tipus_via` - Tipos de Vía
- `md_street_name` - Nombres de calles registradas (relacionadas con municipios)
- `md_direccio` - Direcciones (el campo `nom_via` se mantiene como String para compatibilidad con calles no registradas)

### Scripts SQL

#### Consultas para Municipios

```sql
-- Obtener todos los municipios ordenados por nombre
SELECT id_municipi, codi, nom, comarca, id_provincia
FROM md_municipi
ORDER BY nom;

-- Obtener todos los municipios con paginación (ejemplo: página 1, 20 registros)
SELECT id_municipi, codi, nom, comarca, id_provincia
FROM md_municipi
ORDER BY nom
LIMIT 20 OFFSET 0;

-- Obtener todos los municipios por comunidad autónoma
SELECT m.id_municipi, m.codi, m.nom, m.comarca, m.id_provincia
FROM md_municipi m
INNER JOIN md_provincia p ON m.id_provincia = p.id_provincia
INNER JOIN md_comunitat_autonoma ca ON p.id_comunitat_autonoma = ca.id_comunitat_autonoma
WHERE ca.id_comunitat_autonoma = 1  -- Cambiar por el ID de la comunidad autónoma deseada
ORDER BY m.nom;

-- Contar el total de municipios (útil para paginación)
SELECT COUNT(*) as total FROM md_municipi;

-- Contar municipios por comunidad autónoma
SELECT COUNT(*) as total
FROM md_municipi m
INNER JOIN md_provincia p ON m.id_provincia = p.id_provincia
WHERE p.id_comunitat_autonoma = 1;  -- Cambiar por el ID de la comunidad autónoma deseada
```

## Desarrollo

### Build

```bash
./gradlew build
```

### Tests

```bash
./gradlew test
```

### Publicación

```bash
./gradlew publish
```

## Requisitos

- Java 17+
- Spring Boot 3.x
- PostgreSQL o H2 (para testing)

## Licencia

Apache License 2.0
