# Funcionalidad: Buscar Factura

Desarrollador: Claudio Areco

Esta funcionalidad permite buscar facturas por los siguientes criterios:

- ID (búsqueda exacta)
- Nombre del cliente (búsqueda parcial, sin distinción entre mayúsculas/minúsculas)
- Razón social (búsqueda parcial, sin distinción entre mayúsculas/minúsculas)

Los cambios se aplican en las siguientes capas:
- Repository: implementación de la lógica de búsqueda
- Controller: invocación de la búsqueda desde la lógica principal
