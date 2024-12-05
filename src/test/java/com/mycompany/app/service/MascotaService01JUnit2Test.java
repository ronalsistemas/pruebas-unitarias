package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.MascotaRepositoryImpl;
import com.mycompany.app.repository.PropietarioRepository;
import com.mycompany.app.repository.PropietarioRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MascotaService01JUnit2Test {

    PropietarioRepository propietarioRepository = new PropietarioRepositoryImpl();
    MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
    ExternalService externalService = new ExternalServiceImpl();
    MascotaService mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);

    @Test
    void testRegistrarMascotaCorrectamente(){
        // Arrange(Preparar)
        Propietario propietario = new Propietario("Dany", "Lima", "123456789");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield2");
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert(Afirmar)
        assertNotNull(registrada);
        assertEquals("Garfield2", registrada.getNombre());
    }

    @Test
    @DisplayName("Test buscar mascota por id existente")
    @Tag("two")
    void testBuscarMascotaPorIdExiste(){
        // Arrange(Preparar)
        int id = 1;
        // Act(Actuar)
        Optional<Mascota> mascota = mascotaService.buscarMascotaPorId(id);
        // Assert(Afirmar)
        assertTrue(mascota.isPresent());
    }

    @Test
    @DisplayName("Test buscar mascota por ID no existente")
    void testBuscarMascotaPorIdNoExiste(){
        // Arrange (Preparar)
        int id = 100;
        // Act (Actuar)
        Optional<Mascota> mascota = mascotaService.buscarMascotaPorId(id);
        // Assert (Afirmar)
        assertFalse(mascota.isPresent(), "La mascota no debe de estar presente");
    }

    @Test
    @DisplayName("Test eliminar mascota por ID no existente")
    void testEliminarMascotaPorIdNoExiste(){
        // Arrange (Preparar)
        int idNoExiste = 100;
        // Act (Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.eliminarMascotaPorId(idNoExiste));
        // Assert (Afirmar)
        assertEquals("No se puede eliminar: No se encontró mascota con el ID proporcionado.", exception.getMessage());
    }

}
