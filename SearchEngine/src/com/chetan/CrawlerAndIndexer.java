package com.chetan;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cheta
 */
public class CrawlerAndIndexer {

    public void ReadFile() {
        String title_beg = "<title>";
        String title_end = "</title>";
        String id_beg = "<id>";
        String id_end = "</id>";
        String text_beg = "<text>";
        String text_end = "</text>";

        String ID = null;
        Boolean addToBuffer = false;
        RemoveRedundantData removeRedundantData = new RemoveRedundantData();
        StringBuilder stringBuilder = new StringBuilder();
        DocumentIndexer indexer = new DocumentIndexer();
        int docsToIndex = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(DataHolder.textCollection_chetanPC));
            String s = null;
            while ((s = br.readLine()) != null) {
                //convert to lovwer case;
                s = s.toLowerCase();
                //search for ID
                if (s.contains(id_beg) && s.contains(id_end)) {
                    int beginIndex = s.indexOf(id_beg);
                    int endindex = s.indexOf(id_end);
                    ID = s.substring(beginIndex + id_beg.length(), endindex);
                } else if (s.contains(title_beg) && s.contains(text_end))//serach for title
                {
                    int startIndex = s.indexOf(title_beg);
                    int endIndex = s.indexOf(title_end);
                    String title = s.substring(startIndex + title_beg.length(), endIndex);
                    //System.out.println("Title: " + title);
                    stringBuilder.append(title + "\n");
                } else if (s.contains(text_beg)) {
                    addToBuffer = true;
                    //System.out.println("Start tag found");
                    int beginIndex = s.indexOf(text_beg);
                    String tmp = s.substring(beginIndex + text_beg.length());
                    stringBuilder.append(tmp);
                } else if (s.contains(text_end))//if the end is found
                {
                    //System.out.println("End tag found");
                    int endIndex = s.indexOf(text_end);
                    String tmp = s.substring(0, endIndex);
                    stringBuilder.append(tmp);
                    addToBuffer = false;
                    String redRemoved = removeRedundantData.removeRedundantData(stringBuilder.toString());
                    //System.out.println("After removing uselsess data "+redRemoved);
                    String[] tokens = redRemoved.split("\\s");//containts repeat words
                    //System.out.println("Tokens: "+Arrays.toString(tokens));
                    //remove stop words
                    DataHolder.documentDatas.add(new DocumentData(ID, removeRedundantData.TokensForFF(Arrays.asList(tokens))));//add to list to calculate tf
                    DataHolder.termsDocsArray.add(removeRedundantData.TokensForFF(Arrays.asList(tokens)));
                    Set<String> tokenToIndex = removeRedundantData.RemoveStopWords(Arrays.asList(tokens));
                   DataHolder.docIDList.add(ID);//store the ID of all the docuemnts
//                    addToAllTerms(List<String> s)
                    //index the local document
                    //merge the local index into globalindex
                    indexer.mergeDocIndexInMainIndex(indexer.createIndex(ID, tokenToIndex));
                   // System.out.println("Global index Merged with local index");
                   // System.out.println(DataHolder.mainIndex);
                 //   System.out.println("Iteration: " + docsToIndex);\
                 //my computer couldnt process more than 150 documents, OOM erros. Need more time to do memory managment, future release. To speed up i am using 50
                  System.out.println("Documents processed: " + docsToIndex);  
                 if (docsToIndex > 20)//type number of docs you want to index
                    {
                        break;
                    }
                   
                    docsToIndex++;
                }
                if (addToBuffer) //write to file
                {
                    stringBuilder.append(s);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrawlerAndIndexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerAndIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
