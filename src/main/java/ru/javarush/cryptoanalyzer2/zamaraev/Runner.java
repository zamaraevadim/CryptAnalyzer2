package ru.javarush.cryptoanalyzer2.zamaraev;

import ru.javarush.cryptoanalyzer2.zamaraev.toplevel.Application;
import ru.javarush.cryptoanalyzer2.zamaraev.controller.MainController;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        MainController mainController = new MainController();

        Application application = new Application(mainController);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the operating mode : \n 1 Encryption \n 2 Decoding \n 3 Brute force \n 4 Static analysis");
        int input = scanner.nextInt();
        String mode = "";
        if(input == 1){
            mode = "encode text.txt encrypted.txt 3";
        }else if(input == 2){
            mode = "decode encrypted.txt decrypted.txt 3";
        }else if(input == 3){
            mode = "bruteforce encrypted.txt bruteforce.txt";
        }else if(input == 4){
            mode = "staticanalysis";
        }
        String[] parameters = mode.split(" ");
        Result result = application.run(parameters);
        System.out.println(result);
    }
}
