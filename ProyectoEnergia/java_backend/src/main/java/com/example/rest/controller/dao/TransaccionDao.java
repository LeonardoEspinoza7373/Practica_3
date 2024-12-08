package com.example.rest.controller.dao;

import com.example.rest.controller.dao.implement.AdapterDao;
import com.example.rest.controller.exception.ListEmptyException;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Transaccion;

public class TransaccionDao extends AdapterDao<Transaccion> {
    private Transaccion transaccion;
    private LinkedList<Transaccion> listAll;

    public TransaccionDao() {
        super(Transaccion.class);
    }

    public Transaccion getTransaccion() {
        if (transaccion == null) {
            transaccion = new Transaccion();
        }
        return this.transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public LinkedList<Transaccion> getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        this.persist(this.transaccion);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getTransaccion(), getTransaccion().getIdTransaccion() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) {
    LinkedList<Transaccion> list = getListAll();
    try {
        for (int i = 0; i < list.getSize(); i++) {
            Transaccion t = list.get(i);
            if (t.getIdTransaccion() == id) {
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

}
