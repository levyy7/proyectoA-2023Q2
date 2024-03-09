package org.proyecto;

import org.junit.jupiter.api.Test;

import static org.proyecto.MedidasInternas.calcularDistanciaEuclidiana;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MedidasInternasTest {

    @Test
    void testear_distancias() {
        Punto a = new Punto(new double[]{1.0, 2.0});
        Punto b = new Punto(new double[]{3.0, 4.0});
        assertEquals(2.8284271247461903 , calcularDistanciaEuclidiana(a,b));
    }
}