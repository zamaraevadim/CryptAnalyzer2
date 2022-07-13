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

public class Encoder implements Action{
    @Override
    public Result execute(String[] parameters) {

        String txtFile = parameters[0];
        String encryptedFile = parameters[1];
        Path pathIn = Path.of(PathFinder.getRoot() + txtFile);
        Path pathOut = Path.of(PathFinder.getRoot() + encryptedFile);
        try {
            Files.createFile(pathOut);
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

            String someText = encoderText(text.toString(),key);
            char[] encryptedTextArray = someText.toCharArray();
            for (char c : encryptedTextArray) {
                writer.append(c);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Result(ResultCode.OK,"read all bytes " + pathIn);
    }
    private static String encoderText(String text1, int key) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(text1.split("")));
        StringBuilder stringBuilder = new StringBuilder();
        for(String str : list){
            stringBuilder.append(characterEncoder(str,key));
        }

        return stringBuilder.toString();
    }
    private static String characterEncoder(String a, int key) {
        for (int i = 0; i < Strings.alphabet.length; i++) {
            if(Strings.alphabet[i].equals(a)){
                if(i + key <= Strings.alphabet.length - 1){
                    return Strings.alphabet[i + key];
                }else {
                    return Strings.alphabet[i + key - Strings.alphabet.length];
                }
            }
        }
        return "";
    }
}
