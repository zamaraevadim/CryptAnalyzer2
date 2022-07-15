package ru.javarush.cryptoanalyzer2.zamaraev.commands;

import ru.javarush.cryptoanalyzer2.zamaraev.constans.Strings;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;
import ru.javarush.cryptoanalyzer2.zamaraev.util.PathFinder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StaticAnalysis implements Action {
    @Override
    public Result execute(String[] parameters) {
        String encrypted = parameters[0];
        String dict = parameters[1];
        String staticanalysis = parameters[2];
        Path pathIn = Path.of(PathFinder.getRoot() + encrypted);
        Path dictPath = Path.of(PathFinder.getRoot() + dict);
        Path pathOut = Path.of(PathFinder.getRoot() + staticanalysis);
        try {
            if(!Files.exists(pathOut)){
                Files.createFile(pathOut);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(FileReader readerEncrypt = new FileReader(String.valueOf(pathIn));
            FileReader readerDict = new FileReader(String.valueOf(dictPath));
            FileWriter writer = new FileWriter(String.valueOf(pathOut))) {

            Map<String, Double> mapOfDict = new HashMap<>();

            TreeMap<Double, String> treeMap = new TreeMap<>();

            Map<String, Double> mapOfEncrypt = new HashMap<>();

            TreeMap<Double, String> treeMapEncrypt = new TreeMap<>();

            for(String str: Strings.alphabet){
                mapOfEncrypt.put(str,0.0);
            }

            for(String str: Strings.alphabet){
                mapOfDict.put(str,0.0);
            }

            StringBuilder dictText = new StringBuilder();

            while (readerDict.ready()){
                dictText.append((char) readerDict.read());
            }

            StringBuilder encryptText = new StringBuilder();

            while (readerEncrypt.ready()){
                encryptText.append((char) readerEncrypt.read());
            }

            String[] dictTextArray = dictText.toString().split("");

            for (String str : dictTextArray){
                if(mapOfDict.containsKey(str.toLowerCase())){
                    mapOfDict.put(str.toLowerCase(),mapOfDict.get(str.toLowerCase()) + 1.0);
                }
            }

            String[] encryptTextArray = encryptText.toString().split("");

            for (String str : encryptTextArray){
                if(mapOfEncrypt.containsKey(str.toLowerCase())){
                    mapOfEncrypt.put(str.toLowerCase(),mapOfEncrypt.get(str.toLowerCase()) + 1.0);
                }
            }


            for (Map.Entry<String, Double> entry : mapOfDict.entrySet()) {
                String k = entry.getKey();

                if(treeMap.containsKey(mapOfDict.get(k))){
                    treeMap.put((mapOfDict.get(k) + Math.random() ) / dictTextArray.length * 100000, k);
                }else{
                    treeMap.put((mapOfDict.get(k) + Math.random() ) / encryptTextArray.length * 100000, k);
                }

            }
            int countMap = 1;
            for(var c : treeMap.entrySet()){
                System.out.println(c + "  " + countMap);
                countMap++;
            }
            for (Map.Entry<String, Double> entry : mapOfEncrypt.entrySet()) {
                String key = entry.getKey();
                if(treeMapEncrypt.containsKey(mapOfEncrypt.get(key))){
                    treeMapEncrypt.put((mapOfEncrypt.get(key) + Math.random() ) / encryptTextArray.length * 100000, key);

                }else{
                    treeMapEncrypt.put((mapOfEncrypt.get(key) + Math.random() ) / encryptTextArray.length * 100000, key);
                }

            }


            Map<String,String> finalMap = new HashMap<>();
            for(var s : treeMapEncrypt.entrySet()){
                if(s.getKey() >= treeMap.lastKey()){
                    finalMap.put(s.getValue(),treeMap.get(treeMap.lastKey()));
                }else{
                    finalMap.put(s.getValue(),treeMap.get(treeMap.floorKey(s.getKey())));
                }
            }
//            for(var d : mapOfDict.entrySet()){
//                System.out.print(d + " ");
//            }
            System.out.println("Из словаря");
            int countTreemap = 0;
            for(var d : treeMap.entrySet()){
                System.out.print(d + " |");
                countTreemap++;
            }
            System.out.println();
            System.out.println("Зашифрованный");
            for(var d : treeMapEncrypt.entrySet()){
                System.out.print(d + " |");
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            int count = 0;
            for(var d : finalMap.entrySet()){
                System.out.print(d + " |");
                count++;
            }

            for(var letter : encryptTextArray){
                writer.append(finalMap.get(letter));

            }
            System.out.println();
            System.out.println("Из словаря " + countTreemap);
            System.out.println(Strings.alphabet.length + "!!!!!");
            System.out.println(count);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
