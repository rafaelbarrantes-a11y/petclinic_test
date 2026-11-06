# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2
## Laboratorio Semana 11

Estudiante: Rafael Barrantes Chayña. Rama: `laboratorio-s11`.

Se implementan cinco casos CRUD y las asignaciones y consultas de `vet_specialties`. Las seis clases tienen seis pruebas cada una. La suite completa incluye las pruebas originales: **51 pruebas, 0 fallos, 0 errores y 0 omitidas**, verificadas con `clean test`.

- Informe: [INFORME_LABORATORIO_SEMANA11.docx](INFORME_LABORATORIO_SEMANA11.docx).
- Respaldo de texto: [informe Markdown](docs/INFORME_LABORATORIO_SEMANA11.md).
- Registros y reportes originales: [evidencias/Semana11](evidencias/Semana11/RESUMEN.md).
- Descripción preparada de la PR: [docs/PULL_REQUEST_SEMANA11.md](docs/PULL_REQUEST_SEMANA11.md).

Requisitos: JDK 21 y conexión para la primera descarga de Maven/dependencias. El perfil por defecto es H2 en memoria. No se necesita MySQL para estas pruebas.

```sh
./mvnw -Dtest=VetServiceTest test
./mvnw -Dtest=OwnerServiceTest test
./mvnw -Dtest=SpecialtyServiceTest test
./mvnw -Dtest=VisitServiceTest test
./mvnw -Dtest=TypeServiceTest test
./mvnw -Dtest=VetSpecialtyServiceTest test
./mvnw clean test
```

En Windows, sustituir `./mvnw` por `.\mvnw.cmd`. En IntelliJ seleccionar JDK 21 para el proyecto y Maven. Los registros cloud muestran además rutas locales de JDK, caché, proxy y certificados; no copiar esas rutas a Windows.

Cada carpeta de caso incluye `codigo_pruebas.java.txt`, `ejecucion.txt`, `resultado.txt`, los reportes de Maven y un `LEEME.md` con títulos, explicaciones y pasos exactos para obtener las capturas pendientes. El historial conserva la primera ejecución y el fallo inicial por falta de compilador. No se fabricaron imágenes. En el Word quedan espacios identificados para las capturas y para completar docente/sección.

La guía escribe `SpecialityServiceTest`, pero se conserva `SpecialtyServiceTest`, nombre ya presente en el proyecto. La rúbrica menciona «Departamentos» aunque el procedimiento exige seis casos de PetClinic; esta diferencia se documenta y requiere aclaración del docente.

### Trabajo con Git

Esta rama conserva los commits reales del trabajo realizado para Rafael Barrantes Chayña. No se acreditan aportes de otros integrantes, resolución de conflictos, revisiones ni merges grupales que no ocurrieron. La rama principal y el repositorio del profesor se mantienen sin cambios.

Pull Request creada hacia `main`: [PR #1 — Laboratorio S11](https://github.com/rafaelbarrantes-a11y/petclinic_test/pull/1). Incluye el código, el informe y las evidencias; permanece abierta para revisión.
