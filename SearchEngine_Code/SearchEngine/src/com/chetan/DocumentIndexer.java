package com.chetan;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *Class to create local and global document indexer
 * @author cheta
 */
public class DocumentIndexer {

    
/**
 * MEthod to create index of the given document
 * @param docID
 * @param tempSet
 * @return 
 */
    public HashMap<String, String> createIndex(String docID, Set<String> tempSet) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String word : tempSet) {
            hashMap.put(word, docID);
        }
        return hashMap;
    }

    /**
     * Method to merge the given index into main Index
     *
     * @param hashMap
     */
    public void mergeDocIndexInMainIndex(HashMap<String, String> hashMap) {
        HashMap<String, String> mainIndex = DataHolder.mainIndex;
        Set<String> keySet = hashMap.keySet();//get all the keys

//iterate though all the values using the keys
        for (String key : keySet) 
        {
            //System.out.println("Key: "+key+"\t Value: "+hashMap.get(key));
            //if main index contains the key
            if(mainIndex.containsKey(key))
            {
                //update the existing values
                String oldVal=mainIndex.get(key);
                String newVal=hashMap.get(key);
                //check if both values are not same
                if(!oldVal.equalsIgnoreCase(newVal))
                {
                    //add the new values to mainMap
                    mainIndex.put(key, oldVal+","+newVal);
                }
            }
            else
            {
                mainIndex.put(key,hashMap.get(key) );//put new value
            }
        }

    }

}
