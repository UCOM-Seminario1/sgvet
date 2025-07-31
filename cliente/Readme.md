## Modulo de clientes
- Funcionelidades
- Consultar Historial
# Módulo Cliente - SGVET

Este módulo maneja la gestión de clientes en el sistema SGVET.

## Funcionalidades Implementadas

### 1. Listar Clientes
- Muestra todos los clientes registrados en la base de datos

### 2. Crear Cliente
- Permite crear nuevos clientes (pendiente de implementación completa)

### 3. Eliminar Cliente
- Permite eliminar clientes existentes (pendiente de implementación)

### 4. Buscar Cliente ⭐ **NUEVO**
La funcionalidad de búsqueda incluye las siguientes opciones:

#### 4.1 Búsqueda por ID
- Busca un cliente específico por su ID único
- Retorna un solo cliente si existe

#### 4.2 Búsqueda por Nombre
- Busca clientes cuyo nombre contenga el criterio especificado
- Búsqueda insensible a mayúsculas/minúsculas
- Retorna múltiples resultados si existen coincidencias

#### 4.3 Búsqueda por Apellido
- Busca clientes cuyo apellido contenga el criterio especificado
- Búsqueda insensible a mayúsculas/minúsculas
- Retorna múltiples resultados si existen coincidencias

#### 4.4 Búsqueda por Teléfono
- Busca clientes cuyo número de teléfono contenga el criterio especificado
- Retorna múltiples resultados si existen coincidencias

#### 4.5 Búsqueda General
- Busca en nombre, apellido y teléfono simultáneamente
- Búsqueda insensible a mayúsculas/minúsculas para nombre y apellido
- Retorna todos los clientes que coincidan con cualquiera de los criterios

## Arquitectura

El módulo sigue el patrón MVC (Model-View-Controller):

### Entity (Model)
- `Cliente.java`: Entidad que representa un cliente con sus atributos

### Boundary (View)
- `ClienteUI.java`: Interfaz de usuario con menús interactivos
- `ClienteRepository.java`: Capa de acceso a datos
- `ClienteDbManager.java`: Gestión de conexiones a base de datos

### Control
- `ClienteController.java`: Lógica de negocio y coordinación entre capas

## Cómo Usar

1. Ejecutar la aplicación desde `ClienteUI.java`
2. Seleccionar la opción "4. Buscar cliente" del menú principal
3. Elegir el tipo de búsqueda deseado (1-5)
4. Ingresar el criterio de búsqueda
5. Revisar los resultados mostrados

## Base de Datos

El módulo utiliza Apache Derby como base de datos. Los scripts de inicialización se encuentran en:
- `src/main/resources/db/initClientes.sql`

## Dependencias

- Módulo base (com.sgvet:base:1.0-SNAPSHOT)
- JUnit para pruebas (scope test)
- Java 11
## Modulo de clientes
- Funcionalidades
* Modificar datos de clientes