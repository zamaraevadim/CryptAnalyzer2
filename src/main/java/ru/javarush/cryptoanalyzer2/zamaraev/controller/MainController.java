package ru.javarush.cryptoanalyzer2.zamaraev.controller;

import ru.javarush.cryptoanalyzer2.zamaraev.commands.Action;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.Result;
import ru.javarush.cryptoanalyzer2.zamaraev.entity.ResultCode;
import ru.javarush.cryptoanalyzer2.zamaraev.exception.ApplictionException;

public class MainController {
    public Result execute (String command, String[] parameters){
        try {
            Action action = Actions.find(command);
            return action.execute(parameters);
        } catch (ApplictionException e) {
            return new Result(ResultCode.ERROR,e.getMessage());
        }
    }
}
