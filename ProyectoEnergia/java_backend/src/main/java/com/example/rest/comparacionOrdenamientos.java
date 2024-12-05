package com.example.rest;
import java.util.Random;

public class comparacionOrdenamientos {

    public void quickSort(int[] array, int izq, int der) {
        if (izq < der) {
            int indicePivote = partition(array, izq, der);
            quickSort(array, izq, indicePivote - 1);
            quickSort(array, indicePivote + 1, der);
        }
    }

    public int partition(int[] array, int izq, int der) {
        int pivote = array[der];
        int i = izq - 1;

        for (int j = izq; j < der; j++) {
            if (array[j] <= pivote) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[der];
        array[der] = temp;
        return i + 1;
    }

    public void mergeSort(int[] array, int izq, int der) {
        if (izq < der) {
            int medio = izq + (der - izq) / 2;
            mergeSort(array, izq, medio);
            mergeSort(array, medio + 1, der);
            mezclar(array, izq, medio, der);
        }
    }

    public void mezclar(int[] array, int izq, int medio, int der) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        int[] arrayIzq = new int[n1];
        int[] arrayDer = new int[n2];

        for (int i = 0; i < n1; i++) {
            arrayIzq[i] = array[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            arrayDer[j] = array[medio + 1 + j];
        }

        int i = 0, j = 0, k = izq;
        while (i < n1 && j < n2) {
            if (arrayIzq[i] <= arrayDer[j]) {
                array[k] = arrayIzq[i];
                i++;
            } else {
                array[k] = arrayDer[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = arrayIzq[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = arrayDer[j];
            j++;
            k++;
        }
    }

    public void shellSort(int[] array) {
        int n = array.length;
        for (int intervalo = n / 2; intervalo > 0; intervalo /= 2) {
            for (int i = intervalo; i < n; i++) {
                int temp = array[i];
                int j;
                for (j = i; j >= intervalo && array[j - intervalo] > temp; j -= intervalo) {
                    array[j] = array[j - intervalo];
                }
                array[j] = temp;
            }
        }
    }

    public void medirTiempos(int n) {
      
        Random random = new Random();
        int[] arregloBase = new int[n];
        for (int i = 0; i < n; i++) {
            arregloBase[i] = random.nextInt(10000); 
        }
    
        
        int[] arrayQuick = arregloBase.clone();
        int[] arrayMerge = arregloBase.clone();
        int[] arrayShell = arregloBase.clone();
    
     
        System.out.println("Arreglo antes de ordenar:");
        imprimirArreglo(arrayQuick);

     
        long inicioQuick = System.nanoTime();
        quickSort(arrayQuick, 0, arrayQuick.length - 1);
        long finQuick = System.nanoTime();
        long tiempoQuick = finQuick - inicioQuick;
    
       
        System.out.println("Arreglo después de QuickSort:");
        imprimirArreglo(arrayQuick);

        
        System.out.println("Arreglo antes de ordenar:");
        imprimirArreglo(arrayMerge);


        long inicioMerge = System.nanoTime();
        mergeSort(arrayMerge, 0, arrayMerge.length - 1);
        long finMerge = System.nanoTime();
        long tiempoMerge = finMerge - inicioMerge;
    
 
        System.out.println("Arreglo después de MergeSort:");
        imprimirArreglo(arrayMerge);
    

        System.out.println("Arreglo antes de ordenar:");
        imprimirArreglo(arrayShell);


        long inicioShell = System.nanoTime();
        shellSort(arrayShell);
        long finShell = System.nanoTime();
        long tiempoShell = finShell - inicioShell;

        System.out.println("Arreglo después de ShellSort:");
        imprimirArreglo(arrayShell);
    
  
        System.out.println("\nResultados para " + n + " elementos:");
        System.out.println("+----------------+----------------+");
        System.out.println("| Algoritmo      | Tiempo (ns)   |");
        System.out.println("+----------------+----------------+");
        System.out.printf("| QuickSort      | %14d |\n", tiempoQuick);
        System.out.printf("| MergeSort      | %14d |\n", tiempoMerge);
        System.out.printf("| ShellSort      | %14d |\n", tiempoShell);
        System.out.println("+----------------+----------------+");
    }
    

    public void imprimirArreglo(int[] array) {
        for (int i = 0; i < Math.min(array.length, 200); i++) { 
            System.out.print(array[i] + " ");
        }
        
        System.out.println();
    }

    public static void main(String[] args) {
        comparacionOrdenamientos comparacionOrdenamientos = new comparacionOrdenamientos();

        // Casos de prueba con diferentes tamaños
        System.out.println("Arreglo con 10,000 datos:");
        comparacionOrdenamientos.medirTiempos(10000);

        System.out.println("\nArreglo con 20,000 datos:");
        comparacionOrdenamientos.medirTiempos(20000);

        System.out.println("\nArreglo con 25,000 datos:");
        comparacionOrdenamientos.medirTiempos(25000);
    }
}
