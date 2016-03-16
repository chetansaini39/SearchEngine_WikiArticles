package com.chetan;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
public class DataHolder implements Serializable{

    static File stopWordsfile = new File("stopwords.dat");
    static File textCollection_chetanPC = new File("testCollection.dat");
    //static List<DocumentData> termsDocsArray= new ArrayList<>();
    static List<String> allTerms = new ArrayList<String>(); //to hold all terms
    static HashMap<String, String> mainIndex = new HashMap<>();
    static List<DocumentData> documentDatas = new ArrayList<>();
   static List<String[]> termsDocsArray = new ArrayList<String[]>();
   // static List<double[]> tfidfDocsVector = new ArrayList<double[]>();
    static List<String> docIDList=new ArrayList<>();
    static List<HashMap<String,Double>> tfOfDocuments= new ArrayList<>();
    static HashMap<String, Double> idfHashMap = new HashMap<String, Double>();//stores IDF of all ther tems in corpus
    static List<HashMap<String,Double>> tfidfValList= new ArrayList<>();//used in TFIDF calculation,stores TFIDF of each document
    static List<double[]> tfidfVectors= new ArrayList<>();//stores tfidf vectors for each document
}
