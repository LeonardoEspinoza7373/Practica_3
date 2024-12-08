package com.example.rest.controller.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.rest.controller.dao.implement.AdapterDao;
import com.example.rest.controller.exception.ListEmptyException;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private static final String FILE_PATH = "/home/leonardo/Escritorio/ProyectoEnergia/java_backend/media/Proyectos.json";  
    private LinkedList listAll;

    public ProyectoDao() {
        super(Proyecto.class);
    }

    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = loadProyectosFromFile();
        }
        return listAll;
    }

    private LinkedList loadProyectosFromFile() {
        LinkedList proyectos = new LinkedList();  
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Proyecto> proyectosList = objectMapper.readValue(new File(FILE_PATH), objectMapper.getTypeFactory().constructCollectionType(List.class, Proyecto.class));

            for (Proyecto proyecto : proyectosList) {
                proyectos.add(proyecto);  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        this.persist(this.proyecto);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) {
        LinkedList list = getListAll();
        try {
            if (list.isEmpty()) {
                return false; 
            }
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto p = (Proyecto) list.get(i);
                if (p.getId() == id) {
                    list.delete(i); 
                    this.listAll = list; 
                    return true; 
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return false; 
    }

    // obtener proyecto por id
    public Proyecto get(int id) {
        LinkedList list = getListAll();
        try {
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto p = (Proyecto) list.get(i);
                if (p.getId() == id) {
                    return p;
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return null; 
    }

    // ordena por id o nombre para quicksort
    public void quickSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        quickSort(proyectos, 0, proyectos.size() - 1, criterio, ascendente);
    }

    private void quickSort(List<Proyecto> proyectos, int low, int high, String criterio, boolean ascendente) {
        if (low < high) {
            int pi = partition(proyectos, low, high, criterio, ascendente);
            quickSort(proyectos, low, pi - 1, criterio, ascendente);
            quickSort(proyectos, pi + 1, high, criterio, ascendente);
        }
    }

    private int partition(List<Proyecto> proyectos, int low, int high, String criterio, boolean ascendente) {
        Proyecto pivot = proyectos.get(high);
        int i = (low - 1);
    
        for (int j = low; j < high; j++) {
            boolean condition;
            if (criterio.equals("id")) {
                condition = Integer.compare(proyectos.get(j).getId(), pivot.getId()) < 0;
            } else {
                condition = proyectos.get(j).getNombre().compareTo(pivot.getNombre()) < 0;
            }
    
            if (ascendente ? condition : !condition) {
                i++;
                Proyecto temp = proyectos.get(i);
                proyectos.set(i, proyectos.get(j));
                proyectos.set(j, temp);
            }
        }
    
        Proyecto temp = proyectos.get(i + 1);
        proyectos.set(i + 1, proyectos.get(high));
        proyectos.set(high, temp);
    
        return i + 1;
    }

    // ordena por id o nombre para mergesort
    public void mergeSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        if (proyectos.size() > 1) {
            int mid = proyectos.size() / 2;
            List<Proyecto> left = new ArrayList<>(proyectos.subList(0, mid));
            List<Proyecto> right = new ArrayList<>(proyectos.subList(mid, proyectos.size()));

            mergeSort(left, criterio, ascendente);
            mergeSort(right, criterio, ascendente);

            merge(proyectos, left, right, criterio, ascendente);
        }
    }

    private void merge(List<Proyecto> proyectos, List<Proyecto> left, List<Proyecto> right, String criterio, boolean ascendente) {
        int i = 0, j = 0, k = 0;
    
        while (i < left.size() && j < right.size()) {
            boolean condition;
            if (criterio.equals("id")) {
                condition = Integer.compare(left.get(i).getId(), right.get(j).getId()) < 0;
            } else {
                condition = left.get(i).getNombre().compareTo(right.get(j).getNombre()) < 0;
            }
    
            if (ascendente ? condition : !condition) {
                proyectos.set(k++, left.get(i++));
            } else {
                proyectos.set(k++, right.get(j++));
            }
        }
    
        while (i < left.size()) {
            proyectos.set(k++, left.get(i++));
        }
    
        while (j < right.size()) {
            proyectos.set(k++, right.get(j++));
        }
    }

     // ordena por id o nombre para shellsort
    public void shellSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        int n = proyectos.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Proyecto temp = proyectos.get(i);
                int j;
                for (j = i; j >= gap && compare(proyectos.get(j - gap), temp, criterio, ascendente); j -= gap) {
                    proyectos.set(j, proyectos.get(j - gap));
                }
                proyectos.set(j, temp);
            }
        }
    }

    //busquedas
    private boolean compare(Proyecto p1, Proyecto p2, String criterio, boolean ascendente) {
        int comparison;
        if (criterio.equals("id")) {
            comparison = Integer.compare(p1.getId(), p2.getId());
        } else {
            comparison = p1.getNombre().compareTo(p2.getNombre());
        }
        return ascendente ? comparison > 0 : comparison < 0;
    }

    // busqueda secuencial
    public int busquedaSecuencial(String criterio, Comparable valor) {
        LinkedList list = getListAll();
        try {
            for (int i = 0; i < list.getSize(); i++) {
                Proyecto p = (Proyecto) list.get(i);
                boolean match = false;

                if (criterio.equals("id") && p.getId() == (int) valor) {
                    match = true;
                } else if (criterio.equals("nombre") && p.getNombre().equals(valor)) {
                    match = true;
                }

                if (match) {
                    return i; 
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    // busqueda binaria
    public int busquedaBinaria(String criterio, Comparable valor) {
        List<Proyecto> proyectos = new ArrayList<>();
        LinkedList list = getListAll();
        try {
            for (int i = 0; i < list.getSize(); i++) {
                proyectos.add((Proyecto) list.get(i));
            }
          
            quickSort(proyectos, criterio, true); 
            int inicio = 0;
            int fin = proyectos.size() - 1;

            while (inicio <= fin) {
                int medio = (inicio + fin) / 2;
                Proyecto proyectoMedio = proyectos.get(medio);
                int comparacion;

                if (criterio.equals("id")) {
                    comparacion = Integer.compare(proyectoMedio.getId(), (int) valor);
                } else {
                    comparacion = proyectoMedio.getNombre().compareTo((String) valor);
                }

                if (comparacion == 0) {
                    return medio; 
                } else if (comparacion < 0) {
                    inicio = medio + 1;
                } else {
                    fin = medio - 1;
                }
            }
        } catch (ListEmptyException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    // seleccionar metodo de busqueda
    public int buscar(String criterio, Comparable valor, boolean esBinaria, String metodo) {
        if (esBinaria) {
            return busquedaBinaria(criterio, valor);
        } else {
            return busquedaSecuencial(criterio, valor);
        }
    }
    
        

    
}
