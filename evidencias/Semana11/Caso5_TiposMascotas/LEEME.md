# Caso 5: Tipos de mascotas

## Evidencia 1 — Código
El código del caso 5 comprueba las operaciones de types con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

Archivo original: src/test/java/com/tecsup/petclinic/services/TypeServiceTest.java.
La copia codigo_pruebas.java.txt conserva el código exacto de esta entrega.

## Evidencia 2 — Ejecución
El registro ejecucion.txt conserva el comando real y toda la salida del proceso.

## Evidencia 3 — Resultado
resultado.txt es un extracto identificado del registro original. Los XML y TXT de Maven están en surefire-reports/.

## Capturas pendientes en IntelliJ / Windows
1. Instalar o seleccionar JDK 21. Abrir pom.xml como proyecto Maven y seleccionar la rama laboratorio-s11.
2. Abrir TypeServiceTest.java; mostrar paquete, nombre de clase, @Test y assertions. Incluir DatabaseServiceTest.java para mostrar @SpringBootTest y @Transactional. Tomar las capturas necesarias con Win+Shift+S y guardarlas como 01_codigo_1.png, 01_codigo_2.png, etc. No recortar el nombre del archivo.
3. Abrir Terminal en la raíz del proyecto y ejecutar exactamente: .\mvnw.cmd -Dtest=TypeServiceTest test
4. Tomar 02_ejecucion.png cuando se vean el comando y el inicio de Maven; esperar el fin del proceso.
5. Tomar 03_resultado.png con el nombre de la clase, Tests run y BUILD SUCCESS. Si hay un fallo, conservarlo y corregir la causa antes de afirmar éxito; no sustituirlo por el registro de otro equipo.
6. Insertar cada captura en el espacio identificado del informe, con los títulos y explicaciones de este archivo. También puede abrir ejecucion.txt para consultar el registro cloud, indicando que es un registro guardado, no una nueva ejecución de Windows.
