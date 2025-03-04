package source.Commands;

import source.ContextExecute;

public class Push implements ICommand{
    private String[] _args;

    public Push(String[] args){
        _args = args;
    }

    public void Execute(ContextExecute contextExecute){
        ValidTest();
        if (!contextExecute.Push(_args[1])) {
            throw new RuntimeException("An attempt to stack an uninitialized variable.");
        }
    }

    public String GetInfo(){
        ValidTest();
        return _args[1] + " is pushed.";
    }

    private void ValidTest(){
        if (_args.length - 1 != 1){
            throw new RuntimeException("Invalid count of argument.");
        }
    }
}
