package org.VMC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller implements AutoCloseable {
    BufferedReader _reader;
    public Controller(){
        _reader = new BufferedReader(new InputStreamReader(System.in));
    }
    public String GetInput() throws IOException {
        if (_reader.ready()){
            return (_reader.readLine());
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        _reader.close();
    }
}
