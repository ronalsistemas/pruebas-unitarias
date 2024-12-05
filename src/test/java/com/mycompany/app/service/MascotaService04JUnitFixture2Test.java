package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.MascotaRepositoryImpl;
import com.mycompany.app.repository.PropietarioRepository;
import com.mycompany.app.repository.PropietarioRepositoryImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MascotaService04JUnitFixture2Test {

    private MascotaService mascotaService;

    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
        PropietarioRepository propietarioRepository = new PropietarioRepositoryImpl(); // Estado compartido
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl(); // Estado compartido
        ExternalService externalService = new ExternalServiceImpl();
        mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);
    }

    @AfterEach
    void afterEach(){
        System.out.println("afterEach");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
    }

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente() {
        // Arrange (Preparar) - Configuración local
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act (Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert (Afirmar) : JUnit
        assertEquals("Garfield", registrada.getNombre());
    }

    @Test
    @DisplayName("Registrar otra mascota correctamente")
    void testRegistrarOtraMascotaCorrectamente() {
        // Arrange (Preparar) - Configuración local
        Propietario propietario = new Propietario("Juan", "Cusco", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Firulais");
        mascota.setPropietario(propietario);

        // Act (Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert (Afirmar) : JUnit
        assertEquals("Firulais", registrada.getNombre());
    }

    @Test
    @DisplayName("Verificar estado del servicio mutado")
    void testVerificarEstadoServicioMutado() {
        // Verifica que las mascotas previamente registradas aún existen
        assertTrue(mascotaService.buscarMascotaPorId(1).isPresent(), "La mascota con ID 1 debería existir.");
        assertTrue(mascotaService.buscarMascotaPorId(2).isPresent(), "La mascota con ID 2 debería existir.");
    }
}
