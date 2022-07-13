package ru.javarush.cryptoanalyzer2.zamaraev.commands;

import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;

public interface Action {
    Result execute(String[] parameters);
}
