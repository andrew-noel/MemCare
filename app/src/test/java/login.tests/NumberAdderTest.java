package login.tests;
/**
 * Created by Noel on 9/20/15.
 */
import org.junit.Test;
import java.util.regex.Pattern;

import login.NumberAdder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberAdderTest {

    @Test
    public void addNumbersTest(){
        int result = NumberAdder.addNumbers(2,4);
        assertEquals(6,result);
    }


}
