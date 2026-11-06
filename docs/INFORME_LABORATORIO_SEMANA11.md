 INFORME DE LABORATORIO

 Pruebas Unitarias con Base de Datos

TECSUP · Semana 11

Curso: Construcción y Pruebas de Software

Estudiante: Rafael Barrantes Chayña

Fecha de ejecución: 06/11/2026 · America/Lima

Repositorio: https://github.com/rafaelbarrantes-a11y/petclinic_test

Rama de trabajo: laboratorio-s11

Docente y sección: [completar según matrícula; la guía y su rúbrica presentan nombres de docente diferentes].

# 1. Objetivos

Verificar la creación, actualización, búsqueda y eliminación de los registros solicitados mediante JUnit y una base de datos real en memoria.

Comprobar las consultas y la eliminación de la relación entre veterinarios y especialidades.

Ejecutar cada caso desde consola y verificar la suite completa sin omitir pruebas.

Conservar código, registros, reportes y cambios en Git para sustentar los resultados.

# 2. Herramientas y configuración

| Herramienta | Versión o configuración |
| --- | --- |
| Java | JDK Temurin 21.0.9; release 21 del proyecto |
| Spring Boot | 3.5.14, sin cambios de versión |
| Maven | 3.9.11 mediante Maven Wrapper |
| Pruebas | JUnit Jupiter, Spring Boot Test y Mockito existentes |
| Persistencia | Spring Data JPA, Hibernate y H2; perfil h2 existente |
| Documento | python-docx; guía original leída desde XML y sus tres imágenes |
| Git | Rama laboratorio-s11; commits descriptivos con identidad ya configurada |

Las clases con @SpringBootTest son pruebas de integración de servicio con base de datos, aunque el laboratorio las denomina pruebas unitarias. Se mantienen las pruebas Mockito existentes. No se cambió el esquema, los datos iniciales ni las versiones del pom.xml.

# 3. Revisión de la guía y del proyecto

Leí GLAB-S11-COLANDA-2026-2.docx completo, sus tablas y sus tres imágenes: portada institucional, árbol de pruebas de IntelliJ y logotipo TECSUP. El procedimiento indica usar PetServiceTest como referencia y desarrollar seis casos con Git. La metodología menciona trabajo en grupo; esta entrega documenta únicamente el trabajo realizado en esta rama, sin atribuir revisiones, conflictos ni aportes de terceros.

El repositorio inicial contenía Pet, PetRepository, PetService y PetServiceImpl; VetServiceTest estaba vacía y OwnerServiceTest y SpecialtyServiceTest tenían ejemplos comentados. Las tablas solicitadas ya existían en schema.sql y data.sql. Añadí las entidades, repositorios e interfaces y servicios Impl necesarios respetando los paquetes reales com.tecsup.petclinic. El texto de CLAUDE.md menciona paquetes com.tecsupt y PostgreSQL, pero estos no coinciden con el código; por eso mantuve la estructura y H2 existentes.

La guía escribe specialities y SpecialityServiceTest; el esquema real usa specialties y la clase existente se llama SpecialtyServiceTest. Conservé ese nombre y utilicé su comando correspondiente. TASKS.md describe otro conjunto de escenarios grupales con Mockito; no lo presento como una ampliación de los seis casos del Word.

Cada prueba nueva usa datos propios, @Transactional y rollback. reload() ejecuta flush() y clear() para que la comprobación lea la base de datos y no solo el mismo objeto administrado. VetSpecialty utiliza una clave compuesta y relaciones @MapsId; Visit enlaza con Pet y Vet, y Pet conserva su ownerId existente.

También conservé todas las pruebas previas, añadí transacciones a PetServiceTest y corregí dos assertions intercambiadas entre ownerId y typeId en cada clase de mascotas. La configuración exclusiva de pruebas dirige el archivo de log a target/test-application.log, porque el perfil original apuntaba al directorio de usuario sin permisos de escritura.

# 4. Resultados reales

| Ejecución | Pruebas | Fallos | Errores | Omitidas | Resultado |
| --- | --- | --- | --- | --- | --- |
| VetServiceTest | 6 | 0 | 0 | 0 | BUILD SUCCESS |
| OwnerServiceTest | 6 | 0 | 0 | 0 | BUILD SUCCESS |
| SpecialtyServiceTest | 6 | 0 | 0 | 0 | BUILD SUCCESS |
| VisitServiceTest | 6 | 0 | 0 | 0 | BUILD SUCCESS |
| TypeServiceTest | 6 | 0 | 0 | 0 | BUILD SUCCESS |
| VetSpecialtyServiceTest | 6 | 0 | 0 | 0 | BUILD SUCCESS |
| Suite general (clean test) | 51 | 0 | 0 | 0 | BUILD SUCCESS |

Las seis clases nuevas o completadas suman 36 pruebas; la ejecución general incluye además las 14 pruebas de mascotas existentes y la prueba de carga de contexto: 51 en total. Las cifras proceden de los XML originales de Surefire, no de una estimación.

Los registros conservan los tiempos UTC originales. La ejecución final se realizó el 6 de noviembre de 2026 por la tarde en Lima. Las rutas y opciones de Maven que aparecen en los registros corresponden a este entorno cloud; las instrucciones de Windows están al final.

# 5. Desarrollo y evidencias de los seis casos

## Caso 1: Veterinarios

Tabla: vets. Creación, actualización de los campos, búsqueda por ID y eliminación. Incluye además búsqueda y actualización de un ID inexistente, con assertions de excepción.

### Evidencia 1: código implementado

Código exacto de src/test/java/com/tecsup/petclinic/services/VetServiceTest.java. La infraestructura compartida se incluye en el anexo.

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class VetServiceTest extends DatabaseServiceTest {
    @Autowired private VetService service;

    @Test
    void testCreateVet() {
        Vet saved = service.create(vet());
        assertNotNull(saved.getId());
        reload();
        Vet actual = service.findById(saved.getId());
        assertEquals("Rafael", actual.getFirstName());
        assertEquals("Barrantes", actual.getLastName());
        assertEquals("vet@example.test", actual.getEmail());
        assertEquals("900000001", actual.getPhone());
        assertEquals(true, actual.getActive());
    }

    @Test
    void testUpdateVet() {
        Vet saved = service.create(vet());
        reload();
        saved.setFirstName("Luis");
        saved.setLastName("Gomez");
        saved.setEmail("nuevo@example.test");
        saved.setPhone("900000009");
        saved.setActive(false);
        Vet updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Vet actual = service.findById(saved.getId());
        assertEquals("Luis", actual.getFirstName());
        assertEquals("Gomez", actual.getLastName());
        assertEquals("nuevo@example.test", actual.getEmail());
        assertEquals("900000009", actual.getPhone());
        assertEquals(false, actual.getActive());
    }

    @Test
    void testFindVetById() {
        Vet saved = service.create(vet());
        reload();
        Vet actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Rafael", actual.getFirstName());
        assertEquals("Barrantes", actual.getLastName());
        assertEquals("vet@example.test", actual.getEmail());
        assertEquals("900000001", actual.getPhone());
        assertEquals(true, actual.getActive());
    }

    @Test
    void testDeleteVet() {
        Vet saved = service.create(vet());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Vet.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindVetByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateVetNotFound() {
        Vet missing = vet();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}

```

### Evidencia 2: ejecución desde consola

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Dtest=VetServiceTest test
```

Registro íntegro: evidencias/Semana11/Caso1_Veterinarios/ejecucion.txt. Contiene el comando, el arranque de Spring, las sentencias SQL y el resultado original de Maven.

### Evidencia 3: resultado

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.876 s -- in com.tecsup.petclinic.services.VetServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  9.874 s
[INFO] Finished at: 2026-11-06T22:07:00Z
Codigo de salida: 0
```

### Evidencia 4: explicación

El código del caso 1 comprueba las operaciones de vets con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

[CAPTURA 1.1 PENDIENTE: editor con el nombre de la clase y sus @Test.]

[CAPTURA 1.2 PENDIENTE: terminal con el comando Maven al iniciar.]

[CAPTURA 1.3 PENDIENTE: terminal con Tests run y BUILD SUCCESS.]

## Caso 2: Dueños

Tabla: owners. Creación, actualización de los campos, búsqueda por ID y eliminación. Incluye además búsqueda y actualización de un ID inexistente, con assertions de excepción.

### Evidencia 1: código implementado

Código exacto de src/test/java/com/tecsup/petclinic/services/OwnerServiceTest.java. La infraestructura compartida se incluye en el anexo.

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceTest extends DatabaseServiceTest {
    @Autowired private OwnerService service;

    @Test
    void testCreateOwner() {
        Owner saved = service.create(owner());
        assertNotNull(saved.getId());
        reload();
        Owner actual = service.findById(saved.getId());
        assertEquals("Ana", actual.getFirstName());
        assertEquals("Perez", actual.getLastName());
        assertEquals("Av. Lima 100", actual.getAddress());
        assertEquals("Lima", actual.getCity());
        assertEquals("900000002", actual.getTelephone());
    }

    @Test
    void testUpdateOwner() {
        Owner saved = service.create(owner());
        reload();
        saved.setFirstName("Maria");
        saved.setLastName("Lopez");
        saved.setAddress("Calle Sur 20");
        saved.setCity("Arequipa");
        saved.setTelephone("900000008");
        Owner updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Owner actual = service.findById(saved.getId());
        assertEquals("Maria", actual.getFirstName());
        assertEquals("Lopez", actual.getLastName());
        assertEquals("Calle Sur 20", actual.getAddress());
        assertEquals("Arequipa", actual.getCity());
        assertEquals("900000008", actual.getTelephone());
    }

    @Test
    void testFindOwnerById() {
        Owner saved = service.create(owner());
        reload();
        Owner actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Ana", actual.getFirstName());
        assertEquals("Perez", actual.getLastName());
        assertEquals("Av. Lima 100", actual.getAddress());
        assertEquals("Lima", actual.getCity());
        assertEquals("900000002", actual.getTelephone());
    }

    @Test
    void testDeleteOwner() {
        Owner saved = service.create(owner());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Owner.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindOwnerByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateOwnerNotFound() {
        Owner missing = owner();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}

```

### Evidencia 2: ejecución desde consola

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Dtest=OwnerServiceTest test
```

Registro íntegro: evidencias/Semana11/Caso2_Duenos/ejecucion.txt. Contiene el comando, el arranque de Spring, las sentencias SQL y el resultado original de Maven.

### Evidencia 3: resultado

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.639 s -- in com.tecsup.petclinic.services.OwnerServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  7.615 s
[INFO] Finished at: 2026-11-06T22:07:09Z
Codigo de salida: 0
```

### Evidencia 4: explicación

El código del caso 2 comprueba las operaciones de owners con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

[CAPTURA 2.1 PENDIENTE: editor con el nombre de la clase y sus @Test.]

[CAPTURA 2.2 PENDIENTE: terminal con el comando Maven al iniciar.]

[CAPTURA 2.3 PENDIENTE: terminal con Tests run y BUILD SUCCESS.]

## Caso 3: Especialidades

Tabla: specialties. Creación, actualización de los campos, búsqueda por ID y eliminación. Incluye además búsqueda y actualización de un ID inexistente, con assertions de excepción.

### Evidencia 1: código implementado

Código exacto de src/test/java/com/tecsup/petclinic/services/SpecialtyServiceTest.java. La infraestructura compartida se incluye en el anexo.

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest extends DatabaseServiceTest {
    @Autowired private SpecialtyService service;

    @Test
    void testCreateSpecialty() {
        Specialty saved = service.create(specialty());
        assertNotNull(saved.getId());
        reload();
        Specialty actual = service.findById(saved.getId());
        assertEquals("Cardiologia S11", actual.getName());
        assertEquals("A101", actual.getOffice());
        assertEquals(8, actual.getHOpen());
        assertEquals(17, actual.getHClose());
    }

    @Test
    void testUpdateSpecialty() {
        Specialty saved = service.create(specialty());
        reload();
        saved.setName("Neurologia S11");
        saved.setOffice("B202");
        saved.setHOpen(9);
        saved.setHClose(18);
        Specialty updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Specialty actual = service.findById(saved.getId());
        assertEquals("Neurologia S11", actual.getName());
        assertEquals("B202", actual.getOffice());
        assertEquals(9, actual.getHOpen());
        assertEquals(18, actual.getHClose());
    }

    @Test
    void testFindSpecialtyById() {
        Specialty saved = service.create(specialty());
        reload();
        Specialty actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Cardiologia S11", actual.getName());
        assertEquals("A101", actual.getOffice());
        assertEquals(8, actual.getHOpen());
        assertEquals(17, actual.getHClose());
    }

    @Test
    void testDeleteSpecialty() {
        Specialty saved = service.create(specialty());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Specialty.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindSpecialtyByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateSpecialtyNotFound() {
        Specialty missing = specialty();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}

```

### Evidencia 2: ejecución desde consola

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Dtest=SpecialtyServiceTest test
```

Registro íntegro: evidencias/Semana11/Caso3_Especialidades/ejecucion.txt. Contiene el comando, el arranque de Spring, las sentencias SQL y el resultado original de Maven.

### Evidencia 3: resultado

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.597 s -- in com.tecsup.petclinic.services.SpecialtyServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  7.617 s
[INFO] Finished at: 2026-11-06T22:07:18Z
Codigo de salida: 0
```

### Evidencia 4: explicación

El código del caso 3 comprueba las operaciones de specialties con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

[CAPTURA 3.1 PENDIENTE: editor con el nombre de la clase y sus @Test.]

[CAPTURA 3.2 PENDIENTE: terminal con el comando Maven al iniciar.]

[CAPTURA 3.3 PENDIENTE: terminal con Tests run y BUILD SUCCESS.]

## Caso 4: Visitas

Tabla: visits. Creación, actualización de los campos, búsqueda por ID y eliminación. Incluye además búsqueda y actualización de un ID inexistente, con assertions de excepción.

La visita crea su propio dueño, tipo, mascota y veterinario. La actualización cambia fecha, descripción, costo y relaciones; la eliminación verifica que los padres se conserven.

### Evidencia 1: código implementado

Código exacto de src/test/java/com/tecsup/petclinic/services/VisitServiceTest.java. La infraestructura compartida se incluye en el anexo.

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class VisitServiceTest extends DatabaseServiceTest {
    @Autowired private VisitService service;

    @Test
    void testCreateVisit() {
        Visit saved = service.create(visit());
        Integer petId = saved.getPet().getId();
        Integer ownerId = saved.getPet().getOwnerId();
        Integer vetId = saved.getVet().getId();
        assertNotNull(saved.getId());
        reload();
        Visit actual = service.findById(saved.getId());
        assertEquals(java.time.LocalDate.of(2026, 11, 6), actual.getVisitDate());
        assertEquals("Consulta S11", actual.getDescription());
        assertEquals(new java.math.BigDecimal("75.50"), actual.getCost());
        assertEquals(petId, actual.getPet().getId());
        assertEquals(ownerId, actual.getPet().getOwnerId());
        assertEquals(vetId, actual.getVet().getId());
    }

    @Test
    void testUpdateVisit() {
        Visit saved = service.create(visit());
        reload();
        Visit newRelations = visit();
        saved.setPet(newRelations.getPet());
        saved.setVet(newRelations.getVet());
        Integer petId = newRelations.getPet().getId();
        Integer ownerId = newRelations.getPet().getOwnerId();
        Integer vetId = newRelations.getVet().getId();
        saved.setVisitDate(java.time.LocalDate.of(2026, 11, 7));
        saved.setDescription("Control S11");
        saved.setCost(new java.math.BigDecimal("100.25"));
        Visit updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Visit actual = service.findById(saved.getId());
        assertEquals(java.time.LocalDate.of(2026, 11, 7), actual.getVisitDate());
        assertEquals("Control S11", actual.getDescription());
        assertEquals(new java.math.BigDecimal("100.25"), actual.getCost());
        assertEquals(petId, actual.getPet().getId());
        assertEquals(ownerId, actual.getPet().getOwnerId());
        assertEquals(vetId, actual.getVet().getId());
    }

    @Test
    void testFindVisitById() {
        Visit saved = service.create(visit());
        reload();
        Visit actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals(java.time.LocalDate.of(2026, 11, 6), actual.getVisitDate());
        assertEquals("Consulta S11", actual.getDescription());
        assertEquals(new java.math.BigDecimal("75.50"), actual.getCost());
    }

    @Test
    void testDeleteVisit() {
        Visit saved = service.create(visit());
        reload();
        Integer petId = saved.getPet().getId();
        Integer ownerId = saved.getPet().getOwnerId();
        Integer vetId = saved.getVet().getId();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Visit.class, saved.getId()));
        assertNotNull(em.find(com.tecsup.petclinic.entities.Pet.class, petId));
        assertNotNull(em.find(com.tecsup.petclinic.entities.Owner.class, ownerId));
        assertNotNull(em.find(com.tecsup.petclinic.entities.Vet.class, vetId));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindVisitByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateVisitNotFound() {
        Visit missing = visit();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}

```

### Evidencia 2: ejecución desde consola

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Dtest=VisitServiceTest test
```

Registro íntegro: evidencias/Semana11/Caso4_Visitas/ejecucion.txt. Contiene el comando, el arranque de Spring, las sentencias SQL y el resultado original de Maven.

### Evidencia 3: resultado

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.979 s -- in com.tecsup.petclinic.services.VisitServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  7.975 s
[INFO] Finished at: 2026-11-06T22:07:27Z
Codigo de salida: 0
```

### Evidencia 4: explicación

El código del caso 4 comprueba las operaciones de visits con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

[CAPTURA 4.1 PENDIENTE: editor con el nombre de la clase y sus @Test.]

[CAPTURA 4.2 PENDIENTE: terminal con el comando Maven al iniciar.]

[CAPTURA 4.3 PENDIENTE: terminal con Tests run y BUILD SUCCESS.]

## Caso 5: Tipos de mascotas

Tabla: types. Creación, actualización de los campos, búsqueda por ID y eliminación. Incluye además búsqueda y actualización de un ID inexistente, con assertions de excepción.

### Evidencia 1: código implementado

Código exacto de src/test/java/com/tecsup/petclinic/services/TypeServiceTest.java. La infraestructura compartida se incluye en el anexo.

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class TypeServiceTest extends DatabaseServiceTest {
    @Autowired private TypeService service;

    @Test
    void testCreateType() {
        Type saved = service.create(type());
        assertNotNull(saved.getId());
        reload();
        Type actual = service.findById(saved.getId());
        assertEquals("Tipo S11", actual.getName());
        assertEquals("Tipo de prueba", actual.getDescription());
        assertEquals(true, actual.getActive());
        assertEquals("small", actual.getSizeCategory());
        assertEquals(10, actual.getAverageLifespan());
        assertEquals("medium", actual.getCareLevel());
    }

    @Test
    void testUpdateType() {
        Type saved = service.create(type());
        reload();
        saved.setName("Tipo actualizado S11");
        saved.setDescription("Descripcion actualizada");
        saved.setActive(false);
        saved.setSizeCategory("medium");
        saved.setAverageLifespan(12);
        saved.setCareLevel("high");
        Type updated = service.update(saved);
        assertEquals(saved.getId(), updated.getId());
        reload();
        Type actual = service.findById(saved.getId());
        assertEquals("Tipo actualizado S11", actual.getName());
        assertEquals("Descripcion actualizada", actual.getDescription());
        assertEquals(false, actual.getActive());
        assertEquals("medium", actual.getSizeCategory());
        assertEquals(12, actual.getAverageLifespan());
        assertEquals("high", actual.getCareLevel());
    }

    @Test
    void testFindTypeById() {
        Type saved = service.create(type());
        reload();
        Type actual = service.findById(saved.getId());
        assertEquals(saved.getId(), actual.getId());
        assertEquals("Tipo S11", actual.getName());
        assertEquals("Tipo de prueba", actual.getDescription());
        assertEquals(true, actual.getActive());
        assertEquals("small", actual.getSizeCategory());
        assertEquals(10, actual.getAverageLifespan());
        assertEquals("medium", actual.getCareLevel());
    }

    @Test
    void testDeleteType() {
        Type saved = service.create(type());
        reload();
        service.delete(saved.getId());
        reload();
        assertNull(em.find(Type.class, saved.getId()));
        assertThrows(NoSuchElementException.class, () -> service.findById(saved.getId()));
    }

    @Test
    void testFindTypeByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(-1));
    }

    @Test
    void testUpdateTypeNotFound() {
        Type missing = type();
        missing.setId(-1);
        assertThrows(NoSuchElementException.class, () -> service.update(missing));
    }
}

```

### Evidencia 2: ejecución desde consola

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Dtest=TypeServiceTest test
```

Registro íntegro: evidencias/Semana11/Caso5_TiposMascotas/ejecucion.txt. Contiene el comando, el arranque de Spring, las sentencias SQL y el resultado original de Maven.

### Evidencia 3: resultado

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.714 s -- in com.tecsup.petclinic.services.TypeServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  7.822 s
[INFO] Finished at: 2026-11-06T22:07:36Z
Codigo de salida: 0
```

### Evidencia 4: explicación

El código del caso 5 comprueba las operaciones de types con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

[CAPTURA 5.1 PENDIENTE: editor con el nombre de la clase y sus @Test.]

[CAPTURA 5.2 PENDIENTE: terminal con el comando Maven al iniciar.]

[CAPTURA 5.3 PENDIENTE: terminal con Tests run y BUILD SUCCESS.]

## Caso 6: Veterinarios y especialidades

Tabla: vet_specialties. Asignación, consulta de especialidades por veterinario, consulta de veterinarios por especialidad y eliminación de una relación. Incluye consultas vacías y rechazo de asignación duplicada.

Las consultas utilizan varios veterinarios y especialidades para comprobar los filtros. La eliminación verifica por SQL que desaparece únicamente el vínculo indicado; conserva los otros vínculos y ambas entidades.

### Evidencia 1: código implementado

Código exacto de src/test/java/com/tecsup/petclinic/services/VetSpecialtyServiceTest.java. La infraestructura compartida se incluye en el anexo.

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class VetSpecialtyServiceTest extends DatabaseServiceTest {
    @Autowired private VetSpecialtyService service;
    private Integer vetId;
    private Integer otherVetId;
    private Integer specialtyId;
    private Integer otherSpecialtyId;

    @BeforeEach
    void fixtures() {
        Vet vet = vet(); em.persist(vet); vetId = vet.getId();
        Vet otherVet = vet(); em.persist(otherVet); otherVetId = otherVet.getId();
        Specialty specialty = specialty(); em.persist(specialty); specialtyId = specialty.getId();
        Specialty otherSpecialty = specialty(); em.persist(otherSpecialty); otherSpecialtyId = otherSpecialty.getId();
        reload();
    }

    @Test
    void testAssignSpecialtyToVet() {
        service.assign(vetId, specialtyId);
        reload();
        VetSpecialty actual = em.find(VetSpecialty.class, new VetSpecialtyId(vetId, specialtyId));
        assertNotNull(actual);
        assertEquals(vetId, actual.getVet().getId());
        assertEquals(specialtyId, actual.getSpecialty().getId());
        assertEquals(0, actual.getYearsExperience());
        assertFalse(actual.getIsPrimary());
        assertEquals(1, countAssignment(vetId, specialtyId));
    }

    @Test
    void testFindSpecialtiesByVet() {
        service.assign(vetId, specialtyId);
        service.assign(vetId, otherSpecialtyId);
        service.assign(otherVetId, specialtyId);
        reload();
        assertEquals(Set.of(specialtyId, otherSpecialtyId), service.findSpecialtiesByVet(vetId)
                .stream().map(Specialty::getId).collect(Collectors.toSet()));
        assertEquals(2, service.findSpecialtiesByVet(vetId).size());
        assertEquals(1, service.findSpecialtiesByVet(otherVetId).size());
    }

    @Test
    void testFindVetsBySpecialty() {
        service.assign(vetId, specialtyId);
        service.assign(otherVetId, specialtyId);
        service.assign(vetId, otherSpecialtyId);
        reload();
        assertEquals(Set.of(vetId, otherVetId), service.findVetsBySpecialty(specialtyId)
                .stream().map(Vet::getId).collect(Collectors.toSet()));
        assertEquals(2, service.findVetsBySpecialty(specialtyId).size());
        assertEquals(1, service.findVetsBySpecialty(otherSpecialtyId).size());
    }

    @Test
    void testRemoveSpecialtyFromVet() {
        service.assign(vetId, specialtyId);
        service.assign(vetId, otherSpecialtyId);
        service.assign(otherVetId, specialtyId);
        reload();
        service.remove(vetId, specialtyId);
        reload();
        assertNull(em.find(VetSpecialty.class, new VetSpecialtyId(vetId, specialtyId)));
        assertEquals(0, countAssignment(vetId, specialtyId));
        assertEquals(Set.of(otherSpecialtyId), service.findSpecialtiesByVet(vetId)
                .stream().map(Specialty::getId).collect(Collectors.toSet()));
        assertEquals(Set.of(otherVetId), service.findVetsBySpecialty(specialtyId)
                .stream().map(Vet::getId).collect(Collectors.toSet()));
        assertNotNull(em.find(Vet.class, vetId));
        assertNotNull(em.find(Specialty.class, specialtyId));
    }

    @Test
    void testFindSpecialtiesByVetEmpty() {
        assertTrue(service.findSpecialtiesByVet(vetId).isEmpty());
        assertTrue(service.findVetsBySpecialty(specialtyId).isEmpty());
    }

    @Test
    void testAssignDuplicateShouldFail() {
        service.assign(vetId, specialtyId);
        reload();
        assertThrows(IllegalArgumentException.class, () -> service.assign(vetId, specialtyId));
        assertEquals(1, countAssignment(vetId, specialtyId));
    }

    private long countAssignment(Integer vet, Integer specialty) {
        return ((Number) em.createNativeQuery(
                "select count(*) from vet_specialties where vet_id = :vet and specialty_id = :specialty")
                .setParameter("vet", vet).setParameter("specialty", specialty).getSingleResult()).longValue();
    }
}

```

### Evidencia 2: ejecución desde consola

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Dtest=VetSpecialtyServiceTest test
```

Registro íntegro: evidencias/Semana11/Caso6_VetEspecialidades/ejecucion.txt. Contiene el comando, el arranque de Spring, las sentencias SQL y el resultado original de Maven.

### Evidencia 3: resultado

```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.977 s -- in com.tecsup.petclinic.services.VetSpecialtyServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  8.110 s
[INFO] Finished at: 2026-11-06T22:07:46Z
Codigo de salida: 0
```

### Evidencia 4: explicación

El código del caso 6 comprueba las operaciones de vet_specialties con assertions de JUnit. El registro confirma la ejecución real del comando Maven. El resumen y los reportes Surefire muestran 6 pruebas, cero fallos, cero errores y cero omisiones.

[CAPTURA 6.1 PENDIENTE: editor con el nombre de la clase y sus @Test.]

[CAPTURA 6.2 PENDIENTE: terminal con el comando Maven al iniciar.]

[CAPTURA 6.3 PENDIENTE: terminal con Tests run y BUILD SUCCESS.]

# 6. Ejecución general y correcciones

```
$ ./mvnw -s /workspace/.onboarding/maven-settings.xml -B -ntp -Dmaven.repo.local=/workspace/.m2/repository -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts clean test
```

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.452 s -- in com.tecsup.petclinic.PetClinicApplicationTests
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.276 s -- in com.tecsup.petclinic.services.SpecialtyServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.197 s -- in com.tecsup.petclinic.services.PetServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.055 s -- in com.tecsup.petclinic.services.OwnerServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.045 s -- in com.tecsup.petclinic.services.TypeServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.764 s -- in com.tecsup.petclinic.services.PetServiceMockitoTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.175 s -- in com.tecsup.petclinic.services.VisitServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.287 s -- in com.tecsup.petclinic.services.VetSpecialtyServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.063 s -- in com.tecsup.petclinic.services.VetServiceTest
[INFO] Tests run: 51, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time:  14.876 s
[INFO] Finished at: 2026-11-06T22:08:02Z
Codigo de salida: 0
```

Los XML y TXT generales se conservan en evidencias/Semana11/General/surefire-reports/. La primera compilación falló porque el Java del sistema no incluía el compilador completo; utilicé el JDK 21 preparado en /workspace/.onboarding/jdk sin cambiar el release del proyecto. Las ejecuciones iniciales pasaron pero registraron avisos de escritura del archivo de log; después de corregir la ruta y reforzar las relaciones de visitas, repetí las seis clases y clean test. El historial está conservado en Historial_primera_ejecucion/.

Persisten advertencias informativas de Hibernate (dialecto H2 explícito y open-in-view) y de la carga dinámica del agente Mockito en Java 21; no son fallos ni pruebas omitidas. No desactivé ninguna prueba.

# 7. Git y Pull Request

Repositorio del estudiante: https://github.com/rafaelbarrantes-a11y/petclinic_test

Rama: laboratorio-s11. Base solicitada: main. El repositorio del profesor jgomezz/petclinic_test no se modificó. No se fusionó la rama principal ni se simularon revisiones de compañeros.

Pull Request: https://github.com/rafaelbarrantes-a11y/petclinic_test/pull/1

La historia real de commits se guarda en evidencias/Semana11/git-log.txt; los commits finales de documentación se consultan también en GitHub. La publicación por git push funciona. La CLI gh no puede consultar api.github.com desde este entorno; para la Pull Request se utiliza el conector GitHub autorizado.

# 8. Revisión de la rúbrica

| Criterio de la guía | Puntaje máximo | Evidencia / estado |
| --- | --- | --- |
| Creación de Test Case para base de datos | 7 | Seis clases; 36 pruebas sobre H2 con persistencia verificada |
| Ejecución de pruebas con Spring desde consola | 6 | Seis registros individuales y clean test, todos exitosos |
| Pruebas de la tabla Departamentos | 7 | Inconsistencia con el procedimiento; requiere aclaración del docente |
| Git y evidencias del procedimiento | Sin puntaje separado en esa tabla | Rama, commits, reportes y registros; capturas identificadas como pendientes |
| Puntualidad, tiempos, ortografía y conclusiones | Descuentos indicados de 1 por criterio | Cinco observaciones y cinco conclusiones; fecha de entrega por validar |

La rúbrica menciona literalmente “Realizar pruebas unitarias para la tabla Departamentos”, pero los seis casos del procedimiento corresponden a PetClinic y no existe esa tabla en el esquema. No inventé un séptimo caso ni asigné una nota; corresponde al docente confirmar cómo aplicará ese criterio. La portada del documento menciona Claudio Olanda Saavedra y la rúbrica Jaime Gómez; debe completarse la portada con el docente de la sección.

# 9. Cinco observaciones

1. Utilicé H2 en memoria para ejecutar las pruebas sin depender de un servidor MySQL externo.

2. Creé datos propios en cada prueba para evitar depender de los identificadores de la carga inicial.

3. Comprobé la persistencia después de vaciar y limpiar el contexto de JPA.

4. Mantuve las pruebas existentes y corregí las comprobaciones que intercambiaban el tipo y el dueño de una mascota.

5. Encontré una diferencia entre los seis casos de PetClinic y la tabla Departamentos mencionada en la rúbrica.

# 10. Cinco conclusiones

1. Puedo comprobar las cuatro operaciones principales de cada tabla con resultados concretos de JUnit.

2. Confirmé que las visitas mantienen relaciones válidas con las mascotas, sus dueños y los veterinarios.

3. Verifiqué que quitar una especialidad de un veterinario conserva las otras asignaciones y los registros principales.

4. Conseguí ejecutar las 51 pruebas del proyecto sin fallos, errores ni omisiones.

5. Dejé los cambios y sus registros organizados para poder repetir las pruebas y sustentar esta entrega.

# 11. Requisitos completos y pendientes

| Requisito | Estado |
| --- | --- |
| Seis casos y todas las operaciones solicitadas | Completo; 36 pruebas |
| Pruebas por separado y suite general | Completo; 6×6 y 51 pruebas generales |
| Registros originales y reportes Maven | Completo; carpeta evidencias/Semana11 |
| Informe Word y código por caso | Completo; este documento y respaldo Markdown |
| Git, commits y rama publicada | Completo; consultar GitHub |
| Pull Request | Creada: https://github.com/rafaelbarrantes-a11y/petclinic_test/pull/1 |
| Capturas reales de código, ejecución y resultado | Pendientes; se entregan registros y pasos exactos, sin imágenes fabricadas |
| Docente y sección de la portada | Completar según matrícula |
| Interpretación de Departamentos en rúbrica | Aclaración pendiente del docente; diferencia documentada |
| Trabajo grupal, revisiones y merge | No acreditados en esta sesión; requieren participación real del equipo si el docente los exige |

# 12. Reproducción en Windows e IntelliJ

Seleccionar JDK 21 como Project SDK y Maven Runner JRE. Abrir la terminal en la carpeta que contiene pom.xml; el perfil por defecto es h2, por lo que no se necesita configurar usuario ni contraseña de MySQL. La primera ejecución necesita conexión para descargar Maven y las dependencias.

```
.\mvnw.cmd -Dtest=VetServiceTest test
.\mvnw.cmd -Dtest=OwnerServiceTest test
.\mvnw.cmd -Dtest=SpecialtyServiceTest test
.\mvnw.cmd -Dtest=VisitServiceTest test
.\mvnw.cmd -Dtest=TypeServiceTest test
.\mvnw.cmd -Dtest=VetSpecialtyServiceTest test
.\mvnw.cmd clean test
```

En Linux o macOS, usar ./mvnw en lugar de .\mvnw.cmd. No copiar las rutas /workspace/.onboarding de los registros cloud a Windows. Si la política de red requiere proxy o certificados, usar la configuración de la institución.

Para cada caso, seguir el LEEME.md de su carpeta. Abrir las pruebas en IntelliJ para las capturas del código y usar Win+Shift+S para capturar la terminal al inicio y al final. Para Git, ejecutar git log --all --graph --oneline y capturar el resultado. Los espacios del informe indican exactamente qué imagen falta; nunca deben reemplazarse por imágenes generadas.

# Anexo A. Infraestructura compartida de pruebas

```
package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Transactional
abstract class DatabaseServiceTest {
    @Autowired protected EntityManager em;

    protected void reload() {
        em.flush();
        em.clear();
    }

    protected Vet vet() {
        Vet value = new Vet();
        value.setFirstName("Rafael"); value.setLastName("Barrantes");
        value.setEmail("vet@example.test"); value.setPhone("900000001"); value.setActive(true);
        return value;
    }

    protected Owner owner() {
        Owner value = new Owner();
        value.setFirstName("Ana"); value.setLastName("Perez");
        value.setAddress("Av. Lima 100"); value.setCity("Lima"); value.setTelephone("900000002");
        return value;
    }

    protected Specialty specialty() {
        Specialty value = new Specialty();
        value.setName("Cardiologia S11"); value.setOffice("A101");
        value.setHOpen(8); value.setHClose(17);
        return value;
    }

    protected Type type() {
        Type value = new Type();
        value.setName("Tipo S11"); value.setDescription("Tipo de prueba"); value.setActive(true);
        value.setSizeCategory("small"); value.setAverageLifespan(10); value.setCareLevel("medium");
        return value;
    }

    protected Visit visit() {
        Owner owner = owner(); em.persist(owner);
        Type type = type(); em.persist(type);
        Pet pet = new Pet(null, "Mascota S11", type.getId(), owner.getId(), LocalDate.of(2024, 1, 1));
        em.persist(pet);
        Vet vet = vet(); em.persist(vet);
        Visit value = new Visit(); value.setPet(pet); value.setVet(vet);
        value.setVisitDate(LocalDate.of(2026, 11, 6)); value.setDescription("Consulta S11");
        value.setCost(new BigDecimal("75.50"));
        return value;
    }
}

```

# Anexo B. Correspondencia entre pruebas y requisitos

VetServiceTest: testCreateVet, testUpdateVet, testFindVetById, testDeleteVet, testFindVetByIdNotFound, testUpdateVetNotFound.

OwnerServiceTest: testCreateOwner, testUpdateOwner, testFindOwnerById, testDeleteOwner, testFindOwnerByIdNotFound, testUpdateOwnerNotFound.

SpecialtyServiceTest: testCreateSpecialty, testUpdateSpecialty, testFindSpecialtyById, testDeleteSpecialty, testFindSpecialtyByIdNotFound, testUpdateSpecialtyNotFound.

VisitServiceTest: testCreateVisit, testUpdateVisit, testFindVisitById, testDeleteVisit, testFindVisitByIdNotFound, testUpdateVisitNotFound.

TypeServiceTest: testCreateType, testUpdateType, testFindTypeById, testDeleteType, testFindTypeByIdNotFound, testUpdateTypeNotFound.

VetSpecialtyServiceTest: testAssignSpecialtyToVet, testFindSpecialtiesByVet, testFindVetsBySpecialty, testRemoveSpecialtyFromVet, testFindSpecialtiesByVetEmpty, testAssignDuplicateShouldFail.
