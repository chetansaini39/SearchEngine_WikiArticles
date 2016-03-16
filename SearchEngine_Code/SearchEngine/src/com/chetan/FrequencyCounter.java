package com.chetan;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cheta
 */
public class FrequencyCounter {

    public void calculateTermFrequencies() {
        System.out.println("Calculating TF..");
        // HashMap<String,Integer> tfHashMap= new HashMap<>();
        //list whcih contains all documents
        List<DocumentData> documentDatas = DataHolder.documentDatas;
        //iterate over all the documents
        for (DocumentData documentData : documentDatas) {
            HashMap<String, Double> tfHashMap = new HashMap<>();//to hold TF values
            //get the terms of the document
            String[] terms = documentData.getTerms();
            //for each term count its number of occurances
            for (String term : terms) {
                double frequency = findTermFreqInArray(terms, term);
                //System.out.println("TF of "+term+": "+frequency);
                tfHashMap.put(term, frequency);
            }
            //put hashmap into list
            DataHolder.tfOfDocuments.add(tfHashMap);
        }
        //System.out.println("Total HashMaps size: "+DataHolder.tfOfDocuments.size());
        System.out.println("TF Caclulations Completed");
    }

    /**
     * Method to calcuate term frequency of a term in an array
     *
     * @param arr
     * @param term
     * @return
     */
    private double findTermFreqInArray(String[] arr, String term) {
        double frequency = 0;
        for (String string : arr) {
            if (string.equalsIgnoreCase(term)) {
                frequency++;
            }
        }
        return frequency / arr.length;
    }

    private void addToTFHashMap(HashMap<String, Integer> tfHashMap, String term, int frequency) {
//        if(tfHashMap.containsKey(term))//if the key is already there, then update old value
//            tfHashMap.get(term);
        tfHashMap.put(term, frequency);

    }

    /**
     * Method to calculate IDF
     */
    public void calculateIDF() {
        System.out.println("Calculating IDF...");
        //get the terms from main indexer1
        HashMap<String, String> mainIndex = DataHolder.mainIndex;
        Set<String> keySet = mainIndex.keySet();//get all the keys
        //list whcih contains all documents
        List<DocumentData> documentDatas = DataHolder.documentDatas;
        int totalNumOfDocs = documentDatas.size();
//find idf of each key
        for (String key : keySet) {
            int keyFoundInDocs = 0;
            for (DocumentData documentData : documentDatas) {
                String[] terms = documentData.getTerms();
                //convert string array to set to avoid duplicates
                Set<String> docTerms = new HashSet<>(Arrays.asList(terms));
                //check if set contains the key
                if (docTerms.contains(key)) {
                    keyFoundInDocs++;
                }

            }
            //System.out.println("Key: "+key+" found in docs: "+keyFoundInDocs);
            double idf = 1 + Math.log(totalNumOfDocs / keyFoundInDocs);
            DataHolder.idfHashMap.put(key, idf);
        }
         System.out.println("IDF Calculations Completed");

    }

    /**
     * Method to calculate IDF value of each term in each of the the documents
     * the result is stored in DataHolder.idfValList
     */
    public void calculateTFIDF() {
System.out.println("Calculating TFIDF...");
        List<HashMap<String, Double>> idfValList = DataHolder.tfidfValList;

        //iterate over IDF values
        HashMap<String, Double> idfHashMap = DataHolder.idfHashMap;//IDF
        List<HashMap<String, Double>> tfOfDocuments = DataHolder.tfOfDocuments;
        Set<String> idfKeySet = idfHashMap.keySet();
        //iterate and print idf
        //System.out.println("IDF \n "+idfHashMap);
        //print tf
        // System.out.println("No of docs "+tfOfDocuments.size());
        //iterate over each document

        for (HashMap<String, Double> tfOfDocument : tfOfDocuments) {
            // System.out.println("TF of document: "+doc+"\n "+tfOfDocument);
            HashMap<String, Double> idfValues = new HashMap<>();
            Set<String> tfkeySet = tfOfDocument.keySet();
            //iterate over tf keyset
            for (String tfkey : tfkeySet) {
                //iterate over idf keyset
                for (String idfKey : idfKeySet) {
                    //does the tfkey contains idf key
                    if (tfkey.equalsIgnoreCase(idfKey)) {
                        double tfidf = tfOfDocument.get(tfkey) * idfHashMap.get(idfKey);;
                        // System.out.println("TFIDF of "+tfkey+" "+tfidf);
                        //as they are related to one document store them in a hashmap and once the document is fisinshed store the hashmap in list.
                        idfValues.put(tfkey, tfidf);
                    }
                }

            }
            // System.out.println("Vectors in this doc: " + idfValues.size());
            // System.out.println("Vectors in global index: " + DataHolder.mainIndex.size());
            //System.out.println("IDF \n "+idfValues);
            idfValList.add(idfValues);
        }
        System.out.println("IDF Calculation done and stored into DataHolder.idfValList");
    }

    public void generateTFIDFVectors() {
        List<HashMap<String, Double>> idfValList = DataHolder.tfidfValList;//contains documents and for each documents contains idf values of terms
        List<double[]> tfidfVectors = DataHolder.tfidfVectors;

//get each list
        for (HashMap<String, Double> hashMap : idfValList) {
            //get all the values

            Collection<Double> dlist = hashMap.values();
            Double[] dArr = dlist.toArray(new Double[0]);
            double[] dr = new double[dArr.length];
            for (int i = 0; i < dr.length; i++) {
                dr[i] = dArr[i];
            }
            //  System.out.println("Size of this doc array "+dArr.length);
            tfidfVectors.add(dr);
        }
        System.out.println("TFIDF Vectors Generated");
    }

    /**
     * Function to display cosine similarity between documents
     */
    public void calculateCosineSim() {
        List<double[]> tfidfVectors = DataHolder.tfidfVectors;
        for (int i = 0; i < tfidfVectors.size(); i++) {
            for (int j = 0; j < tfidfVectors.size(); j++) {
                System.out.println("between " + DataHolder.docIDList.get(i) + " and " + DataHolder.docIDList.get(j) + "  =  "
                        + new CosineSimilarity().cosineSimilarity(
                                tfidfVectors.get(i),
                                tfidfVectors.get(j)
                        )
                );
            }
        }
        
    }

    /**
     * This method calculated TFIDF and cosine similarity for query, compares it 
     * with document and provides list of document relevant to query.
     * @param ss
     * @return 
     */
    public List<DocsRelevantToQuery>  calculateQueryTFIDFAndCosSim(List<String> ss) {
        HashMap<String, Double> queryTF = new HashMap<String, Double>();
        String[] arr = ss.toArray(new String[0]);
        for (String s : ss) {
            double frequency = findTermFreqInArray(arr, s);
            queryTF.put(s, frequency);
        }
        //search for the terms in IDF
        //iterate over IDF values
        HashMap<String, Double> idfHashMap = DataHolder.idfHashMap;//IDF
        HashMap<String, Double> tfidfQueryHashMap = new HashMap<>();//for storing TFIDF values of query
        Set<String> tfKeys = queryTF.keySet();
        for (String tfKey : tfKeys) {
            if (idfHashMap.containsKey(tfKey)) {
                double idfVal = idfHashMap.get(tfKey);
                double tfVal = queryTF.get(tfKey);
                tfidfQueryHashMap.put(tfKey, tfVal * idfVal);
            }
        }
        double[] tfidfVectorQueryVector = new double[tfidfQueryHashMap.size()];
        Collection<Double> dlist = tfidfQueryHashMap.values();
        Double[] dArr = dlist.toArray(new Double[0]);
        for (int i = 0; i < dArr.length; i++) {
            tfidfVectorQueryVector[i] = dArr[i];
        }
        //calculate cosine similarity between query and each document
       return calculateCosineSimQuery(tfidfVectorQueryVector);

    }

    /**
     * Function to display cosine similarity between documents
     */
    private List<DocsRelevantToQuery> calculateCosineSimQuery(double[] queryVector) {
        List<double[]> tfidfVectors = DataHolder.tfidfVectors;
        List<String> docIDList = DataHolder.docIDList;
        //double[] simArr = new double[docIDList.size()];
        List<DocsRelevantToQuery> simList=new ArrayList<>();
        for (int i = 0; i < tfidfVectors.size(); i++) {
            double sim = new CosineSimilarity().cosineSimilarity(queryVector, tfidfVectors.get(i));
            System.out.println("between " + "query" + " and doc " + docIDList.get(i) + "  =  " + sim);
            simList.add(new DocsRelevantToQuery(docIDList.get(i), sim));
            
        }

        System.out.println("Sorting list....\n sorted Data");
        simList.sort(new DocsRelevantToQuery());
        for (DocsRelevantToQuery docsRelevantToQuery : simList) {
            System.out.println(docsRelevantToQuery);
        }
        return simList;
    }
}
