package com.chetan;


import java.io.Serializable;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cheta
 */
public class DocumentData implements Serializable
{
 private String id;
 private String[] terms;
 
    public DocumentData(String id, String[] terms) {
        this.id = id;
        this.terms = terms;
    }

    @Override
    public String toString() {
        String id= String.valueOf(this.getId());
        String termArray=Arrays.toString(this.getTerms());
        return id+": "+termArray;
    }



    /**
     * @return the terms
     */
    public String[] getTerms() {
        return terms;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

 
}
