package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.MascotaRepositoryImpl;
import com.mycompany.app.repository.PropietarioRepository;
import com.mycompany.app.repository.PropietarioRepositoryImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MascotaService01JUnit1Test {

    @Test
    @Tag("one")
    void testRegistrarMascotaCorrectamente() {
        // Arrange(Preparar)
        PropietarioRepository propietarioRepository = new PropietarioRepositoryImpl();
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "123456789");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert(Afirmar) : JUnit
        assertNotNull(registrada);
        assertEquals("Garfield", registrada.getNombre());
        assertNotNull(registrada.getPropietario());
        assertTrue(registrada.getId() > 0);
        assertDoesNotThrow(registrada::getId);
    }

    @Test
    void testTrue(){
        assertTrue(true);
    }
}
