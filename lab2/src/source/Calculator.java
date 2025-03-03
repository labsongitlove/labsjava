package source;

import java.util.Map;
import java.util.Stack;

public class Calculator {

    private Stack<String> _commands;
    private Map<String, Double> _vars;

    public Calculator(String filename){ //TODO usable, first
        MakeStackCommands(filename);
    }

    private void MakeStackCommands(String commands){ //TODO usable

    }

    private void ParseCommand(String Command){ //TODO usable, make return type is Command or <int, String>

    }

    public void DoCommand(){
        //TODO usable, next command in stack
        UpdateLog();
    }

    private void UpdateLog(){ // TODO usable, arg is Command

    }
}
