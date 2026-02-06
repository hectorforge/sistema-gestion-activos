# Asset Management System 🏢

Sistema integral de gestión de activos institucionales basado en una arquitectura moderna de microservicios con Spring Boot, PostgreSQL y Docker.

## 📋 Descripción General

**Asset Management System** es una plataforma escalable diseñada para la administración completa de activos institucionales, incluyendo:

- 📦 **Equipos de cómputo e electrónicos**
- 🪑 **Mobiliario institucional**
- 📊 **Inventario general**
- 💎 **Bienes y activos de la institución**

El sistema proporciona herramientas integrales para registrar, rastrear, reportar y gestionar el ciclo de vida completo de los activos institucionales.

---

## 🏗️ Arquitectura

### Patrón Arquitectónico: Arquitectura Hexagonal (Puertos y Adaptadores)

Nuestro sistema implementa **Clean Architecture** con enfoque hexagonal, proporcionando:

- ✅ **Independencia de frameworks**: La lógica de negocio no depende de herramientas externas
- ✅ **Testabilidad**: Código altamente testeable sin dependencias externas
- ✅ **Mantenibilidad**: Separación clara de responsabilidades
- ✅ **Escalabilidad**: Fácil incorporación de nuevas funcionalidades

```
┌─────────────────────────────────────────────────────┐
│              PUERTO DE ENTRADA (API REST)            │
│                  Spring Boot Controllers             │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│         ADAPTADORES (Controllers, Security)          │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│      CASOS DE USO (Application Services/UseCases)    │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│         ENTIDADES DE DOMINIO (Domain Models)         │
└─────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────┐
│   PUERTO DE SALIDA (Repositories, Persistence)       │
│         PostgreSQL, Cache, External APIs             │
└─────────────────────────────────────────────────────┘
```

### Microservicios

El sistema está compuesto por **6 servicios** distribuidos:

#### 🔑 Servicios de Negocio

1. **Microservicio de Activos (Asset Service)**
   - CRUD de activos institucionales
   - Categorización y clasificación
   - Asignación y seguimiento de activos

2. **Microservicio de Seguridad (Security Service)**
   - Autenticación (JWT)
   - Autorización basada en roles
   - Gestión de permisos y credenciales

3. **Microservicio de Reportes (Report Service)**
   - Generación de reportes personalizados
   - Análisis de activos
   - Exportación de datos (PDF, Excel)

#### 🔧 Servicios de Infraestructura

4. **Config Server**
   - Gestión centralizada de configuración
   - Propiedades dinámicas por ambiente

5. **Service Registry (Eureka Server)**
   - Descubrimiento automático de servicios
   - Balanceo de carga
   - Health checks

6. **API Gateway**
   - Punto de entrada único
   - Enrutamiento inteligente
   - Rate limiting y seguridad perimetral

---

## 🛠️ Stack Tecnológico

### Backend
- **Framework**: Spring Boot 3.x
- **Java**: JDK 11+
- **Build**: Maven
- **Persistencia**: Spring Data JPA

### Base de Datos
- **PostgreSQL**: Almacenamiento principal
- **Flyway/Liquibase**: Migraciones (opcional)

### Cloud & Infraestructura
- **Docker**: Containerización
- **Docker Compose**: Orquestación local
- **Spring Cloud**: Gestión de microservicios
  - Spring Cloud Config
  - Spring Cloud Netflix Eureka
  - Spring Cloud Gateway

### Seguridad
- **Spring Security**: Autenticación y autorización
- **JWT**: Tokens seguros
- **BCrypt**: Encriptación de contraseñas

### Testing
- **JUnit 5**: Framework de testing
- **Mockito**: Mocking
- **TestContainers**: Testing de base de datos

---

## 📦 Estructura del Proyecto

```
asset-management-system/
│
├── 📁 asset-service/              # Microservicio de Activos
│   ├── src/main/java/
│   │   ├── domain/                # Entidades y lógica de negocio
│   │   ├── application/           # Casos de uso y servicios
│   │   ├── infrastructure/        # Persistencia, configuración
│   │   ├── adapter/               # Controllers, DTOs
│   │   └── AssetServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── 📁 security-service/           # Microservicio de Seguridad
│   ├── src/main/java/
│   │   ├── domain/
│   │   ├── application/
│   │   ├── infrastructure/
│   │   ├── adapter/
│   │   └── SecurityServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── 📁 report-service/             # Microservicio de Reportes
│   ├── src/main/java/
│   │   ├── domain/
│   │   ├── application/
│   │   ├── infrastructure/
│   │   ├── adapter/
│   │   └── ReportServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── 📁 config-server/              # Servidor de Configuración
│   ├── src/main/java/
│   │   └── ConfigServerApplication.java
│   ├── config/                    # Archivos de configuración
│   ├── Dockerfile
│   └── pom.xml
│
├── 📁 eureka-server/              # Registro de Servicios
│   ├── src/main/java/
│   │   └── EurekaServerApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── 📁 api-gateway/                # Puerta de Entrada
│   ├── src/main/java/
│   │   ├── config/
│   │   ├── security/
│   │   └── ApiGatewayApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── 📁 docs/                       # Documentación
│   ├── architecture.md
│   ├── api-specifications.md
│   └── deployment.md
│
├── 📁 diagrams/                   # Diagramas de arquitectura
│   ├── system-architecture.png
│   ├── data-flow.png
│   └── deployment-diagram.png
│
├── docker-compose.yml             # Orquestación de servicios
├── .gitignore
├── README.md
└── pom.xml (padre)                # POM padre (multi-módulo)
```

---

## 🚀 Inicio Rápido

### Requisitos Previos

- Java JDK 11+
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL 12+ (si ejecutas sin Docker)

### 1️⃣ Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/asset-management-system.git
cd asset-management-system
```

### 2️⃣ Construir los Servicios

```bash
# Compilar todos los módulos
mvn clean install

# O construir servicios específicos
mvn clean install -pl asset-service
```

### 3️⃣ Ejecutar con Docker Compose

```bash
# Iniciar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down
```

### 4️⃣ Verificar Servicios

| Servicio | URL |
|----------|-----|
| API Gateway | http://localhost:8080 |
| Eureka Server | http://localhost:8761 |
| Config Server | http://localhost:8888 |
| Asset Service | http://localhost:8081 |
| Security Service | http://localhost:8082 |
| Report Service | http://localhost:8083 |

---

## 🔐 Autenticación

El sistema usa **JWT (JSON Web Tokens)** para autenticación.

### Obtener Token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

### Usar Token en Requests

```bash
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  http://localhost:8080/api/assets
```

---

## 📚 Endpoints Principales

### Assets Service

```
GET    /api/assets              # Listar todos los activos
POST   /api/assets              # Crear nuevo activo
GET    /api/assets/{id}         # Obtener activo por ID
PUT    /api/assets/{id}         # Actualizar activo
DELETE /api/assets/{id}         # Eliminar activo
GET    /api/assets/category/{id} # Activos por categoría
```

### Report Service

```
GET    /api/reports             # Listar reportes disponibles
POST   /api/reports/generate    # Generar nuevo reporte
GET    /api/reports/{id}        # Obtener reporte por ID
GET    /api/reports/export/{id} # Exportar reporte (PDF/Excel)
```

### Security Service

```
POST   /api/auth/login          # Autenticarse
POST   /api/auth/logout         # Cerrar sesión
GET    /api/users               # Listar usuarios (admin)
POST   /api/users               # Crear usuario (admin)
```

---

## 🏗️ Principios de Arquitectura Aplicados

### Clean Architecture (Arquitectura Hexagonal)

Cada microservicio sigue estas capas:

```
┌─ Adapter (Entrada)       → Controllers, Request DTOs
├─ Application (Orquestación) → Services, Use Cases
├─ Domain (Lógica)         → Entities, Business Rules
└─ Infrastructure (Salida) → Repositories, Persistence
```

### SOLID Principles

- **S**ingle Responsibility: Cada clase una responsabilidad
- **O**pen/Closed: Abierto para extensión, cerrado para modificación
- **L**iskov Substitution: Interfaces intercambiables
- **I**nterface Segregation: Interfaces específicas
- **D**ependency Inversion: Depender de abstracciones

### Patterns Implementados

- **Repository Pattern**: Abstracción de acceso a datos
- **Dependency Injection**: IoC con Spring
- **Value Objects**: Objetos de dominio sin identidad
- **Entities**: Objetos con identidad única
- **Aggregates**: Raíces de agregados
- **Service Locator**: Mediante Eureka

---

## 🧪 Testing

### Tests Unitarios

```bash
mvn test
```

### Tests de Integración

```bash
mvn verify
```

### Coverage

```bash
mvn jacoco:report
```

---

## 📊 Monitoreo y Logging

### Logging

Cada servicio usa **SLF4J + Logback**:

```properties
# application.yml
logging:
  level:
    com.example: DEBUG
    org.springframework: INFO
  file:
    name: logs/application.log
```

### Health Checks

```bash
curl http://localhost:8081/actuator/health
```

---

## 🔄 CI/CD (Opcional)

Se recomienda configurar:

- **GitHub Actions** o **GitLab CI** para automatizar builds
- **SonarQube** para análisis de código
- **Docker Registry** para almacenar imágenes
- **Kubernetes** para producción

---

## 📝 Configuración por Ambiente

Crea archivos de propiedades por ambiente:

```
├── application.yml           # Configuración por defecto
├── application-dev.yml       # Desarrollo
├── application-prod.yml      # Producción
└── application-docker.yml    # Docker Compose
```

---

## 🐛 Troubleshooting

### El servicio no se conecta a PostgreSQL

```bash
# Verificar que PostgreSQL está corriendo
docker ps | grep postgres

# Revisar logs
docker-compose logs postgres
```

### Eureka no descubre servicios

```bash
# Asegurar que los servicios están registrándose
curl http://localhost:8761/eureka/apps
```

### Port conflicts

Cambiar puertos en `docker-compose.yml` o `application.yml`

---

## 📖 Documentación Adicional

- [Especificación de API](docs/api-specifications.md)
- [Arquitectura Detallada](docs/architecture.md)
- [Guía de Deployment](docs/deployment.md)
- [Convenciones de Código](docs/coding-standards.md)

---

## 👥 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo licencia MIT. Ver archivo `LICENSE` para más detalles.

---

## 📧 Contacto y Soporte

Para soporte o preguntas:
- 📧 Email: support@example.com
- 🐛 Issues: [GitHub Issues](https://github.com/tu-usuario/asset-management-system/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/tu-usuario/asset-management-system/discussions)

---

## 🎯 Roadmap

- [ ] Implementar autenticación OAuth2
- [ ] Dashboard de analytics avanzado
- [ ] Integración con sistemas de inventario externos
- [ ] Mobile App (Flutter)
- [ ] Notificaciones en tiempo real (WebSocket)
- [ ] Exportación de datos avanzada
- [ ] Machine Learning para predicción de mantenimiento

---

**Hecho con ❤️ para la gestión eficiente de activos institucionales**
