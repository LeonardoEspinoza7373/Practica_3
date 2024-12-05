package com.example.rest.controller.dao.implement;
import com.example.rest.controller.tda.list.*;

public interface IntefazDao <T> {
    public void persist(T object) throws Exception;
    public void merge(T object, Integer index) throws Exception;
    public LinkedList<T> listAll();
    public T get(Integer id) throws Exception;
}
