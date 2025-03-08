package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import source.ContextExecute;

public class ContextExecuteTest {
    ContextExecute _contextExecute;

    @BeforeEach
    void Start(){
        _contextExecute = new ContextExecute();
    }

    @Test
    void ValidTest(){
        _contextExecute.UpdateVar("a", 1.0);
        _contextExecute.Push("a");
        Assertions.assertEquals("a = 1.0", _contextExecute.PeekInfo());
        _contextExecute.UpdateVar("b", 2.0);
        Assertions.assertTrue(_contextExecute.Push("b"));
        Assertions.assertEquals("b = 2.0", _contextExecute.PeekInfo());
        Assertions.assertFalse(_contextExecute.Push("c"));
        Assertions.assertEquals("b = 2.0", _contextExecute.PeekInfo());
        _contextExecute.Pop();
        Assertions.assertEquals("a = 1.0", _contextExecute.PeekInfo());
        _contextExecute.Pop();
        Assertions.assertEquals("null", _contextExecute.PeekInfo());
        _contextExecute.Push("a");
        _contextExecute.Clear();
        Assertions.assertEquals("null", _contextExecute.PeekInfo());
        Assertions.assertFalse(_contextExecute.Push("a"));
    }
}
