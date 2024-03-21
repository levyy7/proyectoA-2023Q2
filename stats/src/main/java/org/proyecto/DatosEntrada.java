package org.proyecto;

import java.util.List;

public record DatosEntrada(int dimensiones, int clusters, List<Punto> puntos, List<Punto> centroides,
                           List<Integer> etiquetas, java.math.BigInteger execTime) {
}
