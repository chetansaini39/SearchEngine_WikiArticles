package com.chetan;


import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cheta
 */
public class DocsRelevantToQuery implements Comparable<DocsRelevantToQuery>, Comparator<DocsRelevantToQuery>
{
String ID;
double sim;
    public DocsRelevantToQuery(String ID, double sim) {
        this.ID = ID;
        this.sim = sim;
    }

    public DocsRelevantToQuery() {
    }

  

    @Override
    public int compareTo(DocsRelevantToQuery o) {
        return Double.compare(this.sim,o.sim);
    }

    @Override
    public String toString() {
        return ID+": "+sim; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(DocsRelevantToQuery o1, DocsRelevantToQuery o2) {
       return o2.compareTo(o1);
    }
    
    
    

}
