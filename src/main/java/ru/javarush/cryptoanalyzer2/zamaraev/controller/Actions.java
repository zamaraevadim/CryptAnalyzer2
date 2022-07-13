package ru.javarush.cryptoanalyzer2.zamaraev.controller;

import ru.javarush.cryptoanalyzer2.zamaraev.commands.Action;
import ru.javarush.cryptoanalyzer2.zamaraev.commands.BruteForce;
import ru.javarush.cryptoanalyzer2.zamaraev.commands.Decoder;
import ru.javarush.cryptoanalyzer2.zamaraev.commands.Encoder;

public enum Actions {
    ENCODE(new Encoder()),
    DECODE(new Decoder()),
    BRUTEFORCE(new BruteForce());

    private  final Action action;

    Actions(Action action) {
        this.action = action;
    }

    public static Action find(String command) {
        return Actions.valueOf(command.toUpperCase()).action;
    }
}
