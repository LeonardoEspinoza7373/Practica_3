package com.example.rest.controller.dao.services;

import com.example.rest.controller.dao.ProyectoDao;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;

import java.util.List;

public class ProyectoServices {
    private ProyectoDao proyectoDao;

    public ProyectoServices() {
        this.proyectoDao = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return proyectoDao.save();
    }

    public Boolean update() throws Exception {
        return proyectoDao.update();
    }

    public LinkedList listAll() {
        return proyectoDao.getListAll();
    }

    public Proyecto getProyecto() {
        return proyectoDao.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        proyectoDao.setProyecto(proyecto);
    }


    public Proyecto get(int id) {
        return proyectoDao.get(id);
    }

    public Boolean delete(Integer id) {
        return proyectoDao.delete(id);
    }


    public List getAllProyectos() {
        return proyectoDao.getListAll().toList();
    }

   // metodos para ordenar proyectos
    public void quickSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        proyectoDao.quickSort(proyectos, criterio, ascendente);
    }

   
    public void mergeSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        proyectoDao.mergeSort(proyectos, criterio, ascendente);
    }


    public void shellSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        proyectoDao.shellSort(proyectos, criterio, ascendente);
    }


    public int searchProyecto(String criterio, Comparable valor, boolean esBinaria, String metodo) {
        return proyectoDao.buscar(criterio, valor, esBinaria, metodo);
    }
}