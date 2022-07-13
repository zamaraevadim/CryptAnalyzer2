package ru.javarush.cryptoanalyzer2.zamaraev.commands;

import ru.javarush.cryptoanalyzer2.zamaraev.constans.Strings;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;
import ru.javarush.cryptoanalyzer2.zamaraev.util.PathFinder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;


public class Decoder implements Action{
    @Override
    public Result execute(String[] parameters) {
        String encrypted = parameters[0];
        String decryptedFile = parameters[1];
        Path pathIn = Path.of(PathFinder.getRoot() + encrypted);
        Path pathOut = Path.of(PathFinder.getRoot() + decryptedFile);
        try {
            if(!Files.exists(pathOut)){
                Files.createFile(pathOut);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int key = Integer.parseInt(parameters[2]);

        try(FileReader reader = new FileReader(String.valueOf(pathIn));
            FileWriter writer = new FileWriter(String.valueOf(pathOut))){

            StringBuilder text = new StringBuilder();
            while (reader.ready()){
                text.append((char) reader.read());
            }

            String someText = decoderText(text.toString(),key);
            char[] decryptedTextArray = someText.toCharArray();
            for (char c : decryptedTextArray) {
                writer.append(c);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
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
