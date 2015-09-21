package login.tests;

import org.junit.Test;
import login.NumberAdder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberAdderTest {

    @Test
    public void addNumbersTest(){
        int result = NumberAdder.addNumbers(2,4);
        assertEquals(7,result);
    }


}
