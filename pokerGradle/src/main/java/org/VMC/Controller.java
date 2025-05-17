package org.VMC;

import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller implements AutoCloseable {
    BufferedReader _reader;
    Model _model;
    public Controller(Model model){
        _model = model;

        _reader = new BufferedReader(new InputStreamReader(System.in));
    }
    public void Update() throws IOException, JAXBException {
        if (_reader.ready()){
            _model.UpdateFromController(_reader.readLine());
        }
    }

    @Override
    public void close() throws Exception {
        _reader.close();
    }
}
