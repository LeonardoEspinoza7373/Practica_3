package com.example.rest.controller.dao.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.rest.controller.dao.TransaccionDao;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;
import com.example.rest.models.Transaccion;

public class TransaccionServices {
    private TransaccionDao obj;

    public TransaccionServices() {
        obj = new TransaccionDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Transaccion getTransaccion() {
        return obj.getTransaccion();
    }

    public void setTransaccion(Transaccion transaccion) {
        obj.setTransaccion(transaccion);
    }

    public Transaccion get(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public Boolean delete(int id) {
        return obj.delete(id);
    }

  
    
}
 
