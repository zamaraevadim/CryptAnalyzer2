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
                int stringCount = 0;
                String textBruteForce;
                String finish = "";

                for (int x = 1; true ; x++) {
                    for (int key = 1; key <= Strings.alphabet.length; key++) {
                        textBruteForce = decoderText(text.toString(),key);
                        String[] textArr = textBruteForce.split("");

                        for (int i = 0; i < textArr.length; i++) {
                            if(textArr[i].equals(",")){
                                if(i < textArr.length - 1 && textArr[i + 1].equals(" ")){
                                    count++;
                                }
                            }else if(textArr[i].equals(".")){
                                if(i < textArr.length - 1 && textArr[i + 1].equals(" ")){
                                    count++;
                                }
                            }else if(textArr[i].equals("!")){
                                if(i < textArr.length - 1 && textArr[i + 1].equals(" ")){
                                    count++;
                                }
                            }else if(textArr[i].equals("?")){
                                if(i < textArr.length - 1 && textArr[i + 1].equals(" ")){
                                    count++;
                                }
                            }else if(textArr[i].equals(" ")){
                                count++;
                            }
                        }

                        if(count >= x){
                            stringCount++;
                            finish = textBruteForce;

                        }
                        count = 0;
                    }
                    if(stringCount == 1){
                        char[] bruteForcedTextArray = finish.toCharArray();
                        for (char c : bruteForcedTextArray) {
                            writer.append(c);
                        }
                        break;
                    }
                    stringCount = 0;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        return new Result(ResultCode.OK,"The file " + pathIn.getFileName() +
                " was decrypted using bruteforce " +
                "the decrypted text is in the file " + pathOut.getFileName());

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
