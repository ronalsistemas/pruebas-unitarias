package com.mycompany.app.repository;

import com.mycompany.app.model.Mascota;

import java.util.Optional;

public interface MascotaRepository {
    Mascota guardar(Mascota mascota);
    Optional<Mascota> findById(Integer id);
    Optional<Mascota> findByName(String  nombre);
    void deleteById(Integer id);
}
