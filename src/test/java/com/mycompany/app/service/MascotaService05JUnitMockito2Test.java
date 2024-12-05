package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.PropietarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MascotaService05JUnitMockito2Test {

    @Mock
    PropietarioRepository propietarioRepository;

    @Mock
    MascotaRepository mascotaRepository;

    @Mock
    ExternalService externalService;

    @Test
    void testRegistrarMascotaCorrectamente() {
        // Arrange (Preparar) - Configuración local
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
        MascotaService mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);

        Propietario propietario = new Propietario(1,"Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findByName("Garfield")).thenReturn(Optional.empty());
        when(propietarioRepository.guardar(any(Propietario.class))).thenReturn(propietario);
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);

        // Act (Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert (Afirmar) : JUnit
        assertNotNull(registrada);
        assertEquals("Garfield", registrada.getNombre());
    }
}
