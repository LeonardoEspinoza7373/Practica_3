package com.example.rest.controller.dao;

import com.example.rest.controller.dao.implement.AdapterDao;
import com.example.rest.controller.exception.ListEmptyException;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Transaccion;

public class TransaccionDao extends AdapterDao<Transaccion> {
    private Transaccion transaccion;
    private LinkedList listAll;

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

    public LinkedList getListAll() {
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
        this.merge(getTransaccion(), getTransaccion().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(int id) {
    LinkedList list = getListAll();
    try {
        for (int i = 0; i < list.getSize(); i++) {
            Transaccion t = (Transaccion) list.get(i);
            if (t.getId() == id) {
                list.delete(i);
                this.listAll = list; // Actualizar la lista después de eliminar
                return true; // Operación exitosa
            }
        }
    } catch (ListEmptyException e) {
        e.printStackTrace();
    }
    return false; // Si no se encontró la transacción
}


    public LinkedList order(Integer type_order, String atributo) {
        LinkedList listita = listAll();
        if (!listAll().isEmpty()) {
            Transaccion[] lista = (Transaccion[]) listita.toArray();
            listita.reset();
            for (int i = 1; i < lista.length; i++) {
                Transaccion aux = lista[i];
                int j = i - 1;
                while (j >= 0 && (verify(lista[j], aux, type_order, atributo))) {
                    lista[j + 1] = lista[j--];
                }
                lista[j + 1] = aux;
            }
            listita.toList(lista);
        }
        return listita;
    }

    private Boolean verify(Transaccion a, Transaccion b, Integer type_order, String atributo) {
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("tipoOperacion")) {
                return a.getTipoOperacion().compareTo(b.getTipoOperacion()) > 0;
            } else if (atributo.equalsIgnoreCase("nombreProyecto")) {
                return a.getNombreProyecto().compareTo(b.getNombreProyecto()) > 0;
            } else if (atributo.equalsIgnoreCase("fechaHora")) {
                return a.getFechaHora().isAfter(b.getFechaHora());
            }
        } else {
            if (atributo.equalsIgnoreCase("tipoOperacion")) {
                return a.getTipoOperacion().compareTo(b.getTipoOperacion()) < 0;
            } else if (atributo.equalsIgnoreCase("nombreProyecto")) {
                return a.getNombreProyecto().compareTo(b.getNombreProyecto()) < 0;
            } else if (atributo.equalsIgnoreCase("fechaHora")) {
                return a.getFechaHora().isBefore(b.getFechaHora());
            }
        }
        return false;
    }
}
