package com.chetan;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.snowball.ext.EnglishStemmer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cheta
 */
public class RemoveRedundantData {
EnglishStemmer englishStemmer= new EnglishStemmer();
    public String removeRedundantData(String s) {
        String sTemp = s;
        sTemp = sTemp.replaceAll("[^a-zA-Z0-9 ]", "");
        sTemp = sTemp.replaceAll("\\{.*?\\} ?", "");
        sTemp = sTemp.replaceAll("\\[.*?\\] ?", "");
        sTemp = sTemp.replaceAll("\\===.*?\\=== ?", "");
        sTemp = sTemp.replaceAll("\\b\\w{13,}\\b\\s?", "");
        // sTemp = sTemp.replaceAll("\\[[.*?\\]] ?", "");
        //sTemp = sTemp.replaceAll("\\|.*?\\| ?", "");
        sTemp = sTemp.replaceAll("\\<.*?\\> ?", "");
        
        return sTemp;
    }

    /**
     * Method to remove stop words
     *
     * @param s
     * @return
     */
    public Set<String> RemoveStopWords(List<String> s) {
        //first add to set to avoid duplicates
        Set<String> tempSet = new HashSet<>();
        Set<String> tempSet2= new HashSet<>();
        for (String str : s) {
            tempSet.add(str);
        }
        //now in the set remove the stop words
        tempSet.removeAll(getStopWordsFromfile());
        //do the stemming
        for (String string : tempSet) 
        {
            englishStemmer.setCurrent(string);
            englishStemmer.stem();
            tempSet2.add((englishStemmer.getCurrent()));
        }
        
        return tempSet2;
    }

    private List<String> getStopWordsFromfile() {
        List<String> stopList = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(DataHolder.stopWordsfile));
            String s = null;
            while ((s = br.readLine()) != null) {
                stopList.add(s);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RemoveRedundantData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RemoveRedundantData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stopList;
    }
    
  /**
   * Method returns values for calculating TF IDF tokens
   * @param s
   * @return 
   */
    public String[]  TokensForFF(List<String> s) {
        //first add to set to avoid duplicates 
        
        List<String> tempList= new ArrayList<>();
        for (String string : s) 
        {
        tempList.add(string);
        }
        
        tempList.removeAll(getStopWordsFromfile());
        List<String> stemmedList= new ArrayList<>();
        //do the stemming
        for (String string : tempList) //stem each word and add it to new list
        {
            englishStemmer.setCurrent(string);
            englishStemmer.stem();
            stemmedList.add((englishStemmer.getCurrent()));
        }
        String[] stemmedArray= new String[stemmedList.size()];
        for(int i=0;i<stemmedList.size();i++)
        {
            stemmedArray[i]=stemmedList.get(i);
             DataHolder.allTerms.add(stemmedList.get(i));
        }
        return stemmedArray;
    }

   
    public List<String> NormalizeQuery(String query) {
        
        String s=removeRedundantData(query);
         String[] tokens= s.split("\\s");
        //first add to set to avoid duplicates
        List<String> tempSet = new ArrayList<>();
        List<String> tempSet2= new ArrayList<>();
        for (String str : tokens) {
            tempSet.add(str);
        }
        //now in the set remove the stop words
        tempSet.removeAll(getStopWordsFromfile());
        //do the stemming
        for (String string : tempSet) 
        {
            englishStemmer.setCurrent(string);
            englishStemmer.stem();
            tempSet2.add((englishStemmer.getCurrent()));
        }
        
        return tempSet2;
    }
}
