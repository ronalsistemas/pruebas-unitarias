package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.PropietarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class MascotaService05JUnitMockitoCoverageTest {

    @Mock
    PropietarioRepository propietarioRepository;

    @Mock
    MascotaRepository mascotaRepository;

    @Mock
    ExternalService externalService;

    @InjectMocks
    MascotaService mascotaService;

    @Test
    void testRegistrarMascotaCorrectamente() {
        // Arrange (Preparar) - Configuración local
//        MascotaService mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);

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

    // Regla 01
    @ParameterizedTest
    @NullAndEmptySource
    void testRegistrarMascotaConNombreInvalido(String nombre){
        // Arrange(Preparar)
        Propietario propietario = new Propietario(1, "Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertEquals("El nombre de la mascota no puede estar vacío.",exception.getMessage());
    }

    // Regla 02
    @Test
    void testRegistrarMascotaSinPropietario(){
        // Arrange(Preparar)
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");

        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertEquals("La mascota debe tener un propietario.",exception.getMessage());
    }

    // Regla 03
    @ParameterizedTest
    @NullAndEmptySource
    void testRegistrarMascotaConPropietarioTelefonoInvalido(String telefono){
        // Arrange(Preparar)
        Propietario propietario = new Propietario(1, "Dany", "Lima", telefono);
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act(Actuar)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertEquals("El propietario debe tener un teléfono registrado.",exception.getMessage());
        assertThat(exception, is(instanceOf(IllegalArgumentException.class)));
        assertThat(exception, is(instanceOf(Exception.class)));
    }

    // Regla 04
    @Test
    void testRegistrarMascotaSinVacunas(){
        // Arrange(Preparar)
        Propietario propietario = new Propietario(1, "Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("sin vacuna");
        mascota.setPropietario(propietario);
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(false);

        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertEquals("La mascota no tiene todas las vacunas al día.",exception.getMessage());
    }

    // Regla 05
    @Test
    void testRegistrarMascotaSinRegistroMunicipal(){
        // Arrange(Preparar)
        Propietario propietario = new Propietario(1, "Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(false);

        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertEquals("La mascota no está registrada en el municipio.",exception.getMessage());
    }

    // Regla 06
    @Test
    void testRegistrarMascotaYaRegistrada(){
        // Arrange(Preparar)
        Propietario propietario = new Propietario(1, "Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);
        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findByName(any(String.class))).thenReturn(Optional.of(mascota));

        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertEquals("Esta mascota ya está registrada.",exception.getMessage());
    }

    @Test
    void testRegistrarMascotaConPropietario(){
        // Arrange(Preparar)
        Propietario propietario = new Propietario(1,"Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(eq(mascota))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(eq(mascota))).thenReturn(true);
        when(mascotaRepository.findByName(mascota.getNombre())).thenReturn(Optional.empty());
        when(propietarioRepository.guardar(eq(propietario))).thenReturn(null);

        // Act(Actuar)
        Exception exception = assertThrows(IllegalStateException.class, () -> mascotaService.registrarMascota(mascota));

        // Assert(Afirmar)
        assertThat(exception, instanceOf(IllegalStateException.class));
        assertEquals("No se pudo guardar el propietario.", exception.getMessage());
    }

    @Test
    void testEliminarMascota(){
        // Arrange(Preparar)
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        when(mascotaRepository.findById(any(Integer.class))).thenReturn(Optional.of(mascota));

        // Act(Actuar)
        mascotaService.eliminarMascotaPorId(1);
        when(mascotaRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        Optional<Mascota> result = mascotaService.buscarMascotaPorId(1);

        // Assert(Afirmar)
        assertFalse(result.isPresent(),"La mascota debería haber sido eliminada");
    }

    @Test
    void testEliminarMascotaLanzaExceptionCuandoNoExiste(){
        // Arrange(Preparar)
        int mascotaId = 666;

        // Act(Actuar)
        when(mascotaRepository.findById(mascotaId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> mascotaService.eliminarMascotaPorId(mascotaId));

        // Assert(Afirmar)
        assertEquals("No se puede eliminar: No se encontró mascota con el ID proporcionado.",exception.getMessage());
    }

}
