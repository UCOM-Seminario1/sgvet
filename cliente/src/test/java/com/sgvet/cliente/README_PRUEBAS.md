# Pruebas Unitarias - Módulo de Clientes

Este documento describe las pruebas unitarias creadas para el módulo de clientes, específicamente para la función `modificarCliente`.

## Estructura de Pruebas

### 1. ClienteControllerTest.java
**Ubicación:** `cliente/src/test/java/com/sgvet/cliente/control/ClienteControllerTest.java`

**Pruebas para `modificarCliente`:**
- `modificarCliente_HappyPath_ClienteExiste`: Prueba el caso exitoso cuando el cliente existe
- `modificarCliente_UnhappyPath_ClienteNull`: Prueba cuando se pasa un cliente null

**Pruebas adicionales:**
- `buscarClientePorId_HappyPath_ClienteExiste`: Prueba cuando el cliente existe
- `buscarClientePorId_UnhappyPath_IdInvalido`: Prueba con ID inválido
- `crearCliente_HappyPath_ClienteValido`: Prueba la creación de cliente
- `listarClientes_HappyPath_RetornaLista`: Prueba el listado de clientes

### 2. ClienteRepositoryTest.java
**Ubicación:** `cliente/src/test/java/com/sgvet/cliente/boundary/ClienteRepositoryTest.java`

**Pruebas para `modificarCliente`:**
- `modificarCliente_HappyPath_ClienteExiste`: Prueba la actualización exitosa
- `modificarCliente_UnhappyPath_ClienteNoExiste`: Prueba cuando no se encuentra el cliente

**Pruebas adicionales:**
- `buscarPorId_HappyPath_ClienteExiste`: Prueba la búsqueda exitosa
- `buscarPorId_UnhappyPath_ClienteNoExiste`: Prueba cuando no se encuentra el cliente
- `listarTodos_HappyPath_RetornaLista`: Prueba el listado de todos los clientes
- `insertar_HappyPath_ClienteValido`: Prueba la inserción de cliente
- `buscarPorNombre_HappyPath_RetornaLista`: Prueba búsqueda por nombre
- `buscarPorApellido_HappyPath_RetornaLista`: Prueba búsqueda por apellido
- `buscarPorTelefono_HappyPath_RetornaLista`: Prueba búsqueda por teléfono
- `buscarGeneral_HappyPath_RetornaLista`: Prueba búsqueda general

### 3. ClienteDbManagerTest.java
**Ubicación:** `cliente/src/test/java/com/sgvet/cliente/boundary/ClienteDbManagerTest.java`

**Pruebas para el patrón Singleton:**
- `getInstance_HappyPath_RetornaInstancia`: Prueba que se retorna una instancia válida
- `getInstance_MultipleCalls_RetornaMismaInstancia`: Prueba que siempre se retorna la misma instancia
- `getConnection_HappyPath_RetornaConexion`: Prueba la obtención de conexión
- `constructor_HappyPath_NoLanzaExcepcion`: Prueba la creación de instancia
- `singletonPattern_HappyPath_Consistencia`: Prueba la consistencia del patrón singleton

## Patrones de Prueba Utilizados

### Happy Path
- Pruebas que verifican el comportamiento esperado cuando todo funciona correctamente
- Casos exitosos y normales de uso

### Unhappy Path
- Pruebas que verifican el comportamiento cuando algo falla
- Casos de error, excepciones y valores inesperados

### Nomenclatura
- Formato: `metodo_HappyPath/UnhappyPath_Descripcion`
- Ejemplo: `modificarCliente_HappyPath_ClienteExiste`

## Tecnologías Utilizadas

- **JUnit 4**: Framework de pruebas unitarias
- **Assertions**: Para verificar resultados esperados
- **Pruebas simples**: Sin uso de mocks complejos, enfocadas en verificar que los métodos no fallen

## Características de las Pruebas

### Simplicidad
- Las pruebas son directas y fáciles de entender
- No requieren configuración compleja de mocks
- Se enfocan en verificar que los métodos no lancen excepciones inesperadas

### Robustez
- Manejan casos de error de manera apropiada
- Verifican el comportamiento esperado en diferentes escenarios
- Incluyen comentarios explicativos sobre el comportamiento esperado

## Ejecución de Pruebas

Para ejecutar las pruebas, puedes usar:

```bash
# Ejecutar todas las pruebas del módulo cliente
mvn test -Dtest=*Cliente*Test

# Ejecutar pruebas específicas
mvn test -Dtest=ClienteControllerTest
mvn test -Dtest=ClienteRepositoryTest
mvn test -Dtest=ClienteDbManagerTest
```

## Cobertura de Pruebas

Las pruebas cubren:
- ✅ Controller (ClienteController)
- ✅ Boundary/Repository (ClienteRepository)
- ✅ Manager (ClienteDbManager)
- ✅ Función específica: modificarCliente
- ✅ Funciones adicionales: buscarClientePorId, crearCliente, listarClientes
- ✅ Happy Path y Unhappy Path para cada caso
- ✅ Pruebas simples sin dependencias complejas 