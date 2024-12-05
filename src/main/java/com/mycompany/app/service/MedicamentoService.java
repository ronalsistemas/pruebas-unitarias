package com.mycompany.app.service;

public class MedicamentoService {

    public String registrarMedicamento(String nombre, int cantidad) {
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("Nombre del medicamento no puede ser vacio");
        }
        return "Medicamento registrado";
    }

    public String convertCodeToMedicamento(int code) {
        return switch (code) {
            case 101 -> "Antibiótico";
            case 102 -> "Analgésico";
            case 103 -> "Antiinflamatorio";
            case 104 -> "Vacuna";
            case 105 -> "Desparasitante";
            default -> "Código desconocido";
        };
    }
}
