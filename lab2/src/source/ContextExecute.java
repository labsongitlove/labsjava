package source;

import java.util.HashMap;
import java.util.Stack;

public class ContextExecute {
    private final HashMap<String, Double> _vars = new HashMap<>();
    private final Stack<String> _stack = new Stack<>();

    public void UpdateVar(String key, Double value){
        _vars.put(key, value);
    }

    public boolean Push(String key){
        if (IsVar(key)){
            _stack.push(key);
        }
        return IsVar(key);
    }

    private boolean IsVar(String key){
        return _vars.containsKey(key);
    }

    public String Pop(){
        if(!_stack.isEmpty()){
            return _stack.pop();
        }
        return null;
    }

    public String PeekInfo(){
        if (!_stack.isEmpty()){
            String peekName = _stack.peek();
            Double peekValue = _vars.get(peekName);

            return peekName + " = " + peekValue;
        }
        return "null";
    }

    public Double GetVarValue(String key){
        return _vars.get(key);
    }

    public String GetVars(){
        StringBuilder varsInfo = new StringBuilder();
        for(HashMap.Entry<String, Double> item : _vars.entrySet()){
            varsInfo.append(String.format("%s = %s \n", item.getKey(), item.getValue()));
        }
        return varsInfo.toString();
    }

    public void Clear(){
        _vars.clear();
        _stack.clear();
    }
}
