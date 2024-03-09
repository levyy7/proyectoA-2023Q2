package org.proyecto;

public class MedidasExternas {

    public static double calcularRandIndex(DatosEntrada input1, DatosEntrada input2) {
        int randIndex = 0;
        int n = input1.puntos().size();
        if (n != input2.puntos().size()) {
            System.out.println("error: Calculando rand index de distintos problemas");
        }


        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (input1.etiquetas().get(i) != input1.etiquetas().get(j) && input2.etiquetas().get(i) != input2.etiquetas().get(j))
                    ++randIndex;
                else if (input1.etiquetas().get(i) == input1.etiquetas().get(j) && input2.etiquetas().get(i) == input2.etiquetas().get(j))
                    ++randIndex;
            }
        }

        return (randIndex * 2.0) / (n * (n - 1));
    }
}
