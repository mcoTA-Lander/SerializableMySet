/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author dovdm
 * @param <T>
 */
public class MySet<T> implements Set<T>, Serializable {

    private T[] initialize;
    //final element location as well as the size of set,
    //that is to say: total number of non-null elements
    private int finElm;
    private int size;
    
    public MySet(){
        size = 5;
        finElm = -1;
        initialize = (T[]) new Object[size];
        
    }

    @Override
    public int size() {
        return finElm+1;
    }

    @Override
    public boolean isEmpty() {
        return (initialize.length > 0);
    }

    @Override
    public boolean contains(Object o) {
        for (T val : initialize) { 
            if((val != null)  && (val.equals(o))){
                return true;
            }
        }
        return false;
    }
    
    @Override
    //use null checker
    public boolean add(T t) {
        if(initialize[initialize.length -1] != null){
            T[] arr = initialize.clone();
            initialize = (T[]) new Object[arr.length + 5];
            System.arraycopy(arr, 0, initialize, 0, arr.length);
            initialize[finElm + 1] = t;
            finElm ++;
            size = initialize.length;
            return true;
        }
        if(!this.contains(t)){
            initialize[finElm+1] = t;
            finElm ++;
            return true;
        }
        
        return false;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return new Iterator() {
            int curr = 0;
            @Override
            public boolean hasNext() {
                return (initialize.length > curr && initialize[curr] != null);
            }

            @Override
            public Object next() {
                curr ++;
                return (initialize[curr-1]);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] deepCopy = new Object[this.size()];
        System.arraycopy(initialize, 0, deepCopy, 0, deepCopy.length);
        return deepCopy; // deep copy of elts in Set
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        for(int i = 0; i< this.size(); i++){
            a[i] = (T1) initialize[i];
        }          
        return a;
    }

    @Override
    public boolean remove(Object o) {
        if(initialize[0] == null){
            return false;
        }
        for(int i = 0; i< initialize.length; i++){
            if(initialize[i] != null && initialize[i].equals(o)){
                initialize[i]= initialize[finElm];
                initialize[finElm] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean check = false;
        for(Object val : c){
            check = this.contains(val);
        }
        return check;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean check = false;
        for(Object val : c){
            check = this.contains(val);
            if(!check){
                this.add((T) val);
            }
        }
        return !check;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean check = false;
        for(T val : initialize){
            if((val != null) && !(c.contains(val))){
                check = this.remove(val);
            }
        }
        return check;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean check = false;
        for(Object val: c){
            if(this.contains(val)){
               check = this.remove(val);
            }
        }
        return check;
    }

    @Override
    public void clear() {
        size = initialize.length;
        finElm = -1;
        initialize = (T[]) new Object[size];
    }
    
    //MySet Object Serialization
    @Serial
    private void writeObject(ObjectOutputStream msWrite) throws IOException{
        //set Size
        msWrite.writeInt(size);
        //actual number of values contained
        msWrite.writeInt(finElm);
        
        //write out elements in array
        for(T val : initialize){
            msWrite.writeObject(val);
        }
        
    }
    
    //MySet Object Deserialization
    @Serial
    private void readObject(ObjectInputStream msRead) throws IOException, ClassNotFoundException{
        size = msRead.readInt();
        if(size < 5) throw new IOException();
 
        finElm = msRead.readInt();
        
        initialize = (T[]) new Object[size];
        
        for(int i=0; i< size; i++){
            initialize[i] = (T) msRead.readObject();
        }
    }
}
