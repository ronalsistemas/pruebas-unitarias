package com.mycompany.app.service;


import com.mycompany.app.model.Mascota;

public interface ExternalService {

    boolean validarVacunas(Mascota mascota);
    boolean verificarRegistroMunicipal(Mascota mascota);
}
