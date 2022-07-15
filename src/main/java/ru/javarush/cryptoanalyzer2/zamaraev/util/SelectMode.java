package ru.javarush.cryptoanalyzer2.zamaraev.util;

import java.util.Scanner;

public class SelectMode {
    public static String[] parameters;
    public SelectMode(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the operating mode : \n 1 Encryption \n 2 Decoding \n 3 Brute force \n 4 Static analysis");
        int input = scanner.nextInt();
        String mode = "";
        if(input == 1){
            System.out.println("Enter the key :");
            int key = scanner.nextInt();
            mode = "encode text.txt encrypted.txt " + key;
        }else if(input == 2){
            System.out.println("Enter the key :");
            int key = scanner.nextInt();
            mode = "decode encrypted.txt decrypted.txt " + key;
        }else if(input == 3){
            mode = "bruteforce encrypted.txt bruteforce.txt";
        }else if(input == 4){
            mode = "staticanalysis encrypted.txt dict.txt staticanalysis.txt";
        }else{
            System.out.println("You have selected a non-existing mode");
            System.exit(0);
        }
        parameters = mode.split(" ");
    }

}
