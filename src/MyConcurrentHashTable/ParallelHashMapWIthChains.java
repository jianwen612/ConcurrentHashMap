package MyConcurrentHashTable;

import com.sun.tools.classfile.ConstantPool;
import com.sun.tools.javac.util.Assert;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ParallelHashMapWIthChains<K,V> implements MyConcurrentHashTable<K,V> {
    private int slotSize=8;
    private int size=0;
//    private LinkedList<HashTableEntry<K,V>>[] slots;
    private ArrayList<LinkedList<HashTableEntry<K,V>>> slots;
    private ReentrantLock[] locks;
    public ParallelHashMapWIthChains(){
        slots=new ArrayList<>(slotSize);
        locks=new ReentrantLock[slotSize];
        for(int i=0;i<slotSize;i++){
            slots.add(new LinkedList<HashTableEntry<K, V>>());
            locks[i]=new ReentrantLock();
        }

//        System.out.println(locks[0]);
//        slots=new LinkedList<HashTableEntry<K,V>>[slotSize];
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }


    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V put(K key, V value)
    {
        return putVal(hash(key),key,value);
    }
    private V putVal(int hash, K key, V value){
        int slotIdx=hash&(slotSize-1);
//        System.out.println(slotIdx);
        locks[slotIdx].lock();
        try {
            LinkedList<HashTableEntry<K, V>> slot = slots.get(slotIdx);

            assert slot != null : "slot==null";

//            System.out.println("adding: " + key.toString() + "-" + value.toString());
//            System.out.println("adding finished");
            for (HashTableEntry<K, V> entry : slot) {
                if (entry.getKey() == key) {
                    //already exists
                    V temp = entry.getValue();
                    entry.setValue(value);
                    return temp;
                }
            }
            slot.add(new HashTableEntry<K, V>(key, value));
            slots.set(slotIdx, slot);
            return null;
        }finally {
            locks[slotIdx].unlock();
        }


    }
    @Override
    public V remove(K key) {
        return null;
    }


    @Override
    public void clear() {

    }

    @Override
    public int hash(K key) {
        return key.hashCode();
    }

    @Override
    public synchronized String toString() {
        StringBuilder sb=new StringBuilder();
        int slotIdx=0;
        for(LinkedList<HashTableEntry<K,V>> slot:slots){
            sb.append("slot").append(slotIdx++).append(": ");
            if(slot!=null){
                for(HashTableEntry<K,V> entry: slot){
                    sb.append("key=");
                    sb.append(entry.getKey().toString());
                    sb.append(" val=");
                    sb.append(entry.getValue().toString());
                    sb.append('|');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}