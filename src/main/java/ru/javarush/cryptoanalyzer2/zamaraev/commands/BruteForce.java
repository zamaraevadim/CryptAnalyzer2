package ru.javarush.cryptoanalyzer2.zamaraev.commands;

import ru.javarush.cryptoanalyzer2.zamaraev.constans.Strings;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.ResultCode;
import ru.javarush.cryptoanalyzer2.zamaraev.util.PathFinder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteForce implements Action {

    @Override
    public Result execute(String[] parameters) {
        String encrypted = parameters[0];
        String brutForcedFile = parameters[1];
        Path pathIn = Path.of(PathFinder.getRoot() + encrypted);
        Path pathOut = Path.of(PathFinder.getRoot() + brutForcedFile);
        int keyFinal = 0;
        try {
            if(!Files.exists(pathOut)){
                Files.createFile(pathOut);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            try(FileReader reader = new FileReader(String.valueOf(pathIn));
                FileWriter writer = new FileWriter(String.valueOf(pathOut))){

                StringBuilder text = new StringBuilder();
                while (reader.ready()){
                    text.append((char) reader.read());
                }
                int count = 0;

                int[] keys = new int[Strings.alphabet.length + 1];
                for(int key = 1 ; key <= Strings.alphabet.length; key++){
                    String textBruteForce = decoderText(text.toString(),key);
                    String[] textArray = textBruteForce.split("");
                    String[] textArr = new String[textArray.length/2 + 1];
                    int countArr = 0;
                    for(int x = 0;x < textArray.length ; x++){
                        if(x % 2 == 0){
                            if(x == textArray.length - 1){
                                textArr[countArr] = textArray[x];
                            }else{
                                textArr[countArr] = textArray[x] + textArray[x + 1];
                            }
                            countArr++;

                        }
                    }
                    if(textArr[textArr.length - 1] == null){
                        textArr[textArr.length - 1] = "";
                    }

                    for (String s : textArr) {
                        switch (s) {
                            case ", ", ". ", "? ", "; ", "! ", ": " -> count++;
                        }
                    }
                    keys[key] = count;
                    count = 0;
                }

                int max = Integer.MIN_VALUE;
                for(int x = 0; x < keys.length;x++){
                    if(max < keys[x]){
                        max = keys[x];
                        keyFinal = x;
                    }
                }
                char[] bruteForcedTextArray = decoderText(text.toString(),keyFinal).toCharArray();
                for (char c : bruteForcedTextArray) {
                    writer.append(c);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        return new Result(ResultCode.OK,"The file " + pathIn.getFileName() +
                " was decrypted using bruteforce, key - " + keyFinal +
                ", the decrypted text is in the file " + pathOut.getFileName());

    }
    private static String decoderText(String text2,int key) {
        ArrayList<String> list1 = new ArrayList<>(Arrays.asList(text2.split("")));
        StringBuilder stringBuilder1 = new StringBuilder();
        for(String str : list1){
            stringBuilder1.append(characterDecoder(str,key));
        }

        return stringBuilder1.toString();
    }
    private static String characterDecoder(String s, int key) {
        for (int i = 0; i < Strings.alphabet.length; i++) {
            if(Strings.alphabet[i].equals(s.toLowerCase())){
                if(i - key >= 0){
                    return Strings.alphabet[i - key];
                }else {
                    return Strings.alphabet[i - key + Strings.alphabet.length];
                }
            }
        }
        return "";
    }
}
