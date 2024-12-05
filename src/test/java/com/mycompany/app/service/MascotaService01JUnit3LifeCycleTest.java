package com.mycompany.app.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MascotaService01JUnit3LifeCycleTest {

    int contadorDePrueba = 5;

    @Test
    @DisplayName("A")
    void testA(){
        contadorDePrueba++;
        System.out.println("A: " + contadorDePrueba);
    }

    @Test
    @DisplayName("B")
    @Disabled
    void testB(){
        System.out.println("B: " + contadorDePrueba);
    }

    @Test
    @DisplayName("C")
    void testC(){
        contadorDePrueba=contadorDePrueba+2;
        System.out.println("C: " + contadorDePrueba);
    }

}
