package com.mycompany.app.service;

import com.mycompany.app.model.Mascota;
import com.mycompany.app.model.Propietario;
import com.mycompany.app.repository.MascotaRepository;
import com.mycompany.app.repository.PropietarioRepository;

import java.util.Objects;
import java.util.Optional;

public class MascotaService {
    private final PropietarioRepository propietarioRepository;
    private final MascotaRepository mascotaRepository;
    private final ExternalService externalService;

    public MascotaService(PropietarioRepository propietarioRepository, MascotaRepository repository, ExternalService service) {
        this.propietarioRepository = propietarioRepository;
        this.mascotaRepository = repository;
        this.externalService = service;
    }

    public Mascota registrarMascota(Mascota mascota) {
        // Regla 1: El nombre de la mascota no puede estar vacío.
        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacío.");
        }

        // Regla 2: La mascota debe tener un propietario.
        if (mascota.getPropietario() == null) {
            throw new IllegalArgumentException("La mascota debe tener un propietario.");
        }

        // Regla 3: Verificar que el propietario tenga un teléfono registrado.
        if (mascota.getPropietario().getTelefono() == null || mascota.getPropietario().getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El propietario debe tener un teléfono registrado.");
        }

        // Regla 4: Verificar que la mascota tiene todas las vacunas al día.
        if (!externalService.validarVacunas(mascota)) {
            throw new IllegalStateException("La mascota no tiene todas las vacunas al día.");
        }

        // Regla 5: Verificar el registro municipal de la mascota.
        if (!externalService.verificarRegistroMunicipal(mascota)) {
            throw new IllegalStateException("La mascota no está registrada en el municipio.");
        }

        // Regla 6: La mascota no debe estar ya registrada.
        Optional<Mascota> existente = mascotaRepository.findByName(mascota.getNombre());
        if (existente.isPresent()) {
            throw new IllegalStateException("Esta mascota ya está registrada.");
        }

        // Guardar el propietario
        Propietario propietario = propietarioRepository.guardar(mascota.getPropietario());
        if (Objects.isNull(propietario)) {
            throw new IllegalStateException("No se pudo guardar el propietario.");
        }
        mascota.setPropietario(propietario);

        // Si todas las reglas se cumplen, registramos la mascota.
        Mascota registrada = mascotaRepository.guardar(mascota);
        // Envía una notificación de correo.
        enviarCorreoNotificacion(registrada);
        return registrada;
    }

    public Optional<Mascota> buscarMascotaPorId(Integer id) {
        return mascotaRepository.findById(id);
    }

    public void eliminarMascotaPorId(Integer id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se puede eliminar: No se encontró mascota con el ID proporcionado."));
        mascotaRepository.deleteById(mascota.getId());
    }

    private void enviarCorreoNotificacion(Mascota mascota) {
        // Simulación de envío de correo
        System.out.println("Enviando correo de notificación para la mascota registrada: " + mascota.getNombre());
    }
}
