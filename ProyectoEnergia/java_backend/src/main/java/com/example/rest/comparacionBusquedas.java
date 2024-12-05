package com.example.rest;

import java.util.Arrays;
import java.util.Random;

public class comparacionBusquedas {

    public int busquedaSecuencial(int[] arreglo, int valor) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == valor) {
                return i; 
            }
        }
        return -1; 
    }

    public int busquedaBinaria(int[] arreglo, int valor) {
        int izq = 0;
        int der = arreglo.length - 1;
        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            if (arreglo[medio] == valor) {
                return medio; 
            }
            if (arreglo[medio] < valor) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }
        return -1; 
    }

    public void medirTiempos(int n) {
    
        Random random = new Random();
        int[] arreglo = new int[n];
        for (int i = 0; i < n; i++) {
            arreglo[i] = random.nextInt(10000); 
        }

        int valorABuscar = random.nextInt(10000);

     
        long inicioSecuencial = System.nanoTime();
        busquedaSecuencial(arreglo, valorABuscar);
        long finSecuencial = System.nanoTime();
        long tiempoSecuencial = finSecuencial - inicioSecuencial;

      
        Arrays.sort(arreglo);
        long inicioBinaria = System.nanoTime();
        busquedaBinaria(arreglo, valorABuscar);
        long finBinaria = System.nanoTime();
        long tiempoBinaria = finBinaria - inicioBinaria;

        System.out.println("\nResultados para " + n + " elementos:");
        System.out.println("+-------------------+-------------------+");
        System.out.println("| Algoritmo         | Tiempo (ns)       |");
        System.out.println("+-------------------+-------------------+");
        System.out.printf("| Búsqueda Secuencial| %17d |\n", tiempoSecuencial);
        System.out.printf("| Búsqueda Binaria  | %17d |\n", tiempoBinaria);
        System.out.println("+-------------------+-------------------+");
    }

    public static void main(String[] args) {
        comparacionBusquedas comparacion = new comparacionBusquedas();

        // Casos de prueba con diferentes tamaños
        System.out.println("Arreglo con 10,000 datos:");
        comparacion.medirTiempos(10000);

        System.out.println("\nArreglo con 20,000 datos:");
        comparacion.medirTiempos(20000);

        System.out.println("\nArreglo con 25,000 datos:");
        comparacion.medirTiempos(25000);
    }
}
