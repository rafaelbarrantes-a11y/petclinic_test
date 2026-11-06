# Laboratorio S11: seis casos de pruebas con base de datos y evidencias

El proyecto solo implementaba la capa de mascotas y dejaba vacías las clases de veterinarios, dueños y especialidades. Esta rama añade las entidades, repositorios y servicios necesarios para ejecutar los seis casos de la guía GLAB-S11-COLANDA-2026-2.docx con la base H2 existente.

- Cinco casos CRUD y el caso de asignación y consultas de veterinarios/especialidades: 36 pruebas con JUnit, @SpringBootTest y transacciones con rollback.
- Las comprobaciones vacían y limpian JPA antes de releer los registros; las relaciones incluyen controles de persistencia y eliminación sin perder los padres u otros vínculos.
- Conserva las pruebas existentes; aísla PetServiceTest y corrige assertions intercambiadas de ownerId/typeId. Mantiene Java 21, Spring Boot 3.5.14, el esquema y los datos iniciales.
- Incluye informe Word, respaldo Markdown, registros completos, reportes Surefire individuales y generales, y pasos de captura para Windows/IntelliJ.

Validación real: seis ejecuciones individuales de 6 pruebas cada una y `./mvnw clean test`: **51 pruebas, 0 fallos, 0 errores y 0 omitidas; BUILD SUCCESS**. Los comandos completos del entorno cloud están en `evidencias/Semana11/*/ejecucion.txt`.

Pendientes identificados: capturas gráficas (se entregan registros originales y espacios en el informe), completar docente/sección y aclarar con el docente la mención a «Departamentos» en la rúbrica. No se simulan trabajo grupal, revisiones, conflictos ni merges. No se modifica el repositorio del profesor ni se fusiona main.
