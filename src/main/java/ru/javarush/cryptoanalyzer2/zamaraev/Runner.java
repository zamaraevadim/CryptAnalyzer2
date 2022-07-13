package ru.javarush.cryptoanalyzer2.zamaraev;

import ru.javarush.cryptoanalyzer2.zamaraev.toplevel.Application;
import ru.javarush.cryptoanalyzer2.zamaraev.controller.MainController;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;

public class Runner {
    public static void main(String[] args) {
        MainController mainController = new MainController();

        Application application = new Application(mainController);
        Result result = application.run(args);
        System.out.println(result);
    }
}
