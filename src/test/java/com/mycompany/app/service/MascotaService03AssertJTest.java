package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.MascotaRepositoryImpl;
import com.mycompany.app.repository.PropietarioRepository;
import com.mycompany.app.repository.PropietarioRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MascotaService03AssertJTest {

    PropietarioRepository propietarioRepository = new PropietarioRepositoryImpl();
    MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
    ExternalService externalService = new ExternalServiceImpl();
    MascotaService mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);

    @Test
    @DisplayName("Test registrar mascota correctamente")
    void testRegistrarMascota() {
        // Arrange (Preparar)
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act (Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert (Afirmar) : AssertJ
        assertThat(registrada).isNotNull();
        assertThat(registrada.getId()).isNotNull();
        assertThat(registrada.getNombre()).isEqualTo("Garfield");
        assertThat(registrada.getNombre()).isInstanceOf(String.class);

        // Text Assertions
        assertThat(registrada.getNombre()).startsWith("Ga");
        assertThat(registrada.getNombre()).startsWithIgnoringCase("GA");
        assertThat(registrada.getNombre()).contains("fiel");
        assertThat(registrada.getNombre()).endsWith("ld");
        assertThat(registrada.getNombre()).isNotBlank();
        assertThat(registrada.getNombre()).isNotEmpty();
        assertThat(registrada.getNombre()).hasSize(8);

        // Number Assertions
        assertThat(registrada.getId()).isLessThanOrEqualTo(1);
        assertThat(registrada.getId()).isGreaterThanOrEqualTo(1);
        assertThat(registrada.getId()).isLessThan(10);
        assertThat(registrada.getId()).isGreaterThan(0);

        // Bean Assertions
        assertThat(registrada).hasFieldOrPropertyWithValue("nombre", "Garfield");
        assertThat(registrada).hasFieldOrPropertyWithValue("id", 1);
        assertThat(registrada).hasFieldOrProperty("propietario");

    }

}
