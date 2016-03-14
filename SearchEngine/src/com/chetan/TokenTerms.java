package com.chetan;


import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cheta
 */
public class TokenTerms 
{
    private List<String> tokenTerms= new ArrayList<String>();

    private TokenTerms() {
    }
    private static TokenTerms instance=null;
public static TokenTerms getinstance()
{
    if(instance==null)
    {
        instance=new TokenTerms();
    }
    return instance;
}
    
    
    /**
     * @return the tokenTerms
     */
    public List<String> getTokenTerms() {
        return tokenTerms;
    }

    /**
     * @param tokenTerms the tokenTerms to set
     */
    public void setTokenTerms(List<String> tokenTerms) {
        this.tokenTerms = tokenTerms;
    }
    
    public void addTerm(String term)
    {
        if(!tokenTerms.contains(term))
        {
            tokenTerms.add(term);
        }
    }
    
    public void addTokenArray(String[] arr)
    {
        for (String string : arr) {
             if(!tokenTerms.contains(string))
        {
            tokenTerms.add(string);
        }
        }
    }
}
