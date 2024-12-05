package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.MascotaRepositoryImpl;
import com.mycompany.app.repository.PropietarioRepository;
import com.mycompany.app.repository.PropietarioRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MascotaService02HamcrestTest {

    PropietarioRepository propietarioRepository = new PropietarioRepositoryImpl();
    MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
    ExternalService externalService = new ExternalServiceImpl();
    MascotaService mascotaService = new MascotaService(propietarioRepository, mascotaRepository, externalService);

    @Test
    @DisplayName("Test registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente(){
        // Arrange (Preparar)
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        // Act (Actuar)
        Mascota registrada = mascotaService.registrarMascota(mascota);

        // Assert (Afirmar) : Hamcrest

        // Core Matchers
        assertThat(registrada, notNullValue());
        assertThat(registrada, is(notNullValue()));
        assertThat(registrada.getId(), not(nullValue()));
        assertThat(registrada.getNombre(), is("Garfield"));
        assertThat(registrada.getPropietario(), is(not(nullValue())));
        assertThat(registrada.getNombre(), is(notNullValue(String.class)));
        assertThat(registrada.getNombre(), is(instanceOf(String.class)));
        assertThat(registrada.getNombre(), isA(String.class));

        // Text Matchers: Verifican contenido y estructura de cadenas.
        assertThat(registrada.getNombre(), startsWith("Ga"));
        assertThat(registrada.getNombre(), startsWithIgnoringCase("GA"));
        assertThat(registrada.getNombre(), containsString("fiel"));
        assertThat(registrada.getNombre(), endsWith("ld"));
        assertThat(registrada.getNombre(), endsWithIgnoringCase("LD"));
        assertThat(registrada.getNombre(), equalToIgnoringCase("garfield"));
        assertThat(registrada.getPropietario().getNombre(), not(blankString()));
        assertThat(registrada.getPropietario().getNombre(), not(blankOrNullString()));
        assertThat(registrada.getPropietario().getNombre(), not(emptyString()));
        assertThat(registrada.getPropietario().getNombre(), not(emptyOrNullString()));
        assertThat(registrada.getNombre(), matchesPattern("[A-Z][a-z]+"));
        assertThat(registrada.getNombre(), stringContainsInOrder("a", "f", "l"));
        assertThat(registrada.getNombre(), hasLength(8));

        // Core Matchers - Combinación: Utilizados para combinar condiciones.
        assertThat(registrada.getNombre(), allOf(startsWith("Ga"), containsString("fiel"), endsWith("ld")));
        assertThat(registrada.getNombre(), anyOf(startsWith("Ga"), containsString("fiel"), endsWith("ld")));
        assertThat(registrada.getNombre(), both(startsWith("Ga")).and(containsString("fiel")));
        assertThat(registrada.getNombre(), either(startsWith("Ga")).or(containsString("fiel")));

        // Number Matchers: Comparaciones numéricas.
        assertThat(registrada.getNombre().length(), is(lessThanOrEqualTo(8)));
        assertThat(registrada.getId(), is(greaterThan(0)));
        assertThat(registrada.getId(), is(greaterThanOrEqualTo(1)));
        assertThat(registrada.getId(), is(lessThan(10)));
        assertThat(registrada.getId(), is(lessThanOrEqualTo(4)));
        assertThat(0.0 / 0, is(notANumber()));
        assertThat(Math.sqrt(-1), is(notANumber()));

        // Bean Matchers: Verifican propiedades específicas de un objeto.
        assertThat(registrada, hasProperty("nombre", is("Garfield")));
        assertThat(registrada, hasProperty("id", is(1)));
        assertThat(registrada, hasProperty("propietario", is(notNullValue())));

    }
}
