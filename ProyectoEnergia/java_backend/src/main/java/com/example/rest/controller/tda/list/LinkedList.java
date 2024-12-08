package com.example.rest.controller.tda.list;
import com.example.rest.controller.exception.ListEmptyException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    /**
     * Clase que permite realizar una lista enlazada
     **/
    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;

            // this.size++;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {

        addLast(info);
    }

    // ********** GET */
    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        }
        return header.getInfo();
    }

    private E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }

    }

    /********* END GET */
    /*** ADD BY POSITION */
    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {

            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BY POSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {

            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;

    }

    public E[] toArray(E[] array) {
        if (array.length < size) {
            array = (E[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size);
        }
        Node<E> current = header;
        for (int i = 0; i < size; i++) {
            array[i] = current.getInfo();
            current = current.getNext();
        }
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public List<E> toList() {
        List<E> list = new ArrayList<>();
        Node<E> current = header;
        while (current != null) {
            list.add(current.getInfo());
            current = current.getNext();
        }
        return list;
    }

    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            // 2 5 6 9 --> 2
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }

    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) > 0;
                }
                // break;

            default:
                // mayor a menor
                if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    // "casa" > "pedro"
                    return (a.toString()).compareTo(b.toString()) < 0;
                }
                // break;
        }

    }

    // compare class
    
    private Boolean atrribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {           
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }
        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {              
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }
            }
        }
        if (method != null) {            
            return method.invoke(a);
        }
        
        return null;
    }

    //quick sort
    public void quickSort(E[] array, int bajo, int alto, Integer type) {
        if (bajo < alto) {
            int indicePivote = partition(array, bajo, alto, type);
            quickSort(array, bajo, indicePivote - 1, type);
            quickSort(array, indicePivote + 1, alto, type);
        }
    }

    public int partition(E[] array, int bajo, int alto, Integer type) {
        E pivote = array[alto];

        int i = (bajo - 1);

        for (int j = bajo; j < alto; j++) {
            if (!compare(array[j], pivote, type)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        E temp = array[i + 1];
        array[i + 1] = array[alto];
        array[alto] = temp;
        return i + 1;
    }

    // merge sort
    public void mergeSort(E[] array, int izq, int der, Integer type){
        if(izq < der){
            int medio = izq + (der - izq) / 2;
            mergeSort(array, izq, medio, type);
            mergeSort(array, medio + 1, der, type);
            mezclar(array, izq, medio, der, type);
        }
    }

    public void mezclar(E[] array, int izq, int medio, int der, Integer type){
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        E[] arrayIzq = (E[]) new Object[n1];
        E[] arrayDer = (E[]) new Object[n2];

        System.arraycopy(array, izq, arrayIzq, 0, n1);
        System.arraycopy(array, medio + 1, arrayDer, 0, n2);

        int i = 0, j = 0, k = izq;
        while(i < n1 && j < n2){
            if(!compare(arrayIzq[i], arrayDer[j], type)){
                array[k++] = arrayIzq[i++];
            }else{
                array[k++] = arrayDer[j++];
            }
        }
        while (i < n1) {
            array[k++] = arrayIzq[i++];
        }
        while (j < n2) {
            array[k++] = arrayDer[j++]; 
        }
       
    }
    
    //shell sort
    public void shellSort(E[] array, Integer type){
        int n = array.length;
        for(int intervalo = n / 2; intervalo > 0; intervalo /= 2){
            for(int i = intervalo; i < n; i++){
                E temp = array[i];
                int j;
                for(j = i; j >= intervalo && compare(array[j - intervalo], temp, type); j -= intervalo){
                    array[j] = array[j - intervalo];
                }
                array[j] = temp;
            }
        }
    }

 

    //metodo busqueda secuencial numeros 
    public int busquedaSecuencialNumeros(int[] array, int valor) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == valor) {
                return i;
            }
        }
        return -1;
    }

    //metodo busqueda secuencial objetos
    public int busquedaSecuencial(Object[] arreglo, Object valor) {
    for (int i = 0; i < arreglo.length; i++) {
        if (arreglo[i].equals(valor)) {
            return i;  
        }
    }
    return -1; 
    }

    //metodo busqueda binaria numeros
    public int busquedaBinariaNumeros(int[] array, int valor) {
        int inicio = 0;
        int fin = array.length - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (array[medio] == valor) {
                return medio;
            } else if (array[medio] < valor) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    //metodo busqueda binaria objetos
    
    public int busquedaBinaria(Comparable[] arreglo, Comparable valor) {
        int inicio = 0;
        int fin = arreglo.length - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;

            int comparacion = arreglo[medio].compareTo(valor);

            if (comparacion == 0) {
                return medio;  
            } else if (comparacion < 0) {
                inicio = medio + 1;  
            } else {
                fin = medio - 1;  
            }
        }
        return -1;  
    }

    
}
