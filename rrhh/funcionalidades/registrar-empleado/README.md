# Funcionalidad: Registrar Nuevo Empleado

## Descripción
Implementación del caso de uso "Registrar nuevo empleado" del módulo de Recursos Humanos (RRHH).

## Funcionalidad a realizar
- Permitir el registro de nuevos empleados en el sistema veterinario
- Validar datos obligatorios (nombre, apellido, cédula, cargo)
- Almacenar información del empleado en la base de datos
- Mostrar confirmación del registro exitoso

## Campos del empleado
- ID (generado automáticamente)
- Nombre (obligatorio)
- Apellido (obligatorio) 
- Cédula (obligatorio)
- Teléfono
- Correo electrónico
- Cargo (obligatorio)
- Especialidad (opcional)

## Cargos disponibles
- Veterinario
- Asistente
- Recepcionista
- Administrativo
- Otro

## Archivos del módulo
- `RRHH.java` - Entidad empleado (ya existe)
- `RRHHController.java` - Lógica de negocio (ya existe)
- `RRHHUI.java` - Interfaz de usuario (ya existe)
- `RRHHRepository.java` - Acceso a datos (ya existe)
- `RRHHDbManager.java` - Gestión de base de datos (ya existe) 