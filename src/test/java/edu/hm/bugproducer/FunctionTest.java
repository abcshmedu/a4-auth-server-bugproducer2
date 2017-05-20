package edu.hm.bugproducer;


import edu.hm.bugproducer.restAPI.TokenUtils;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class FunctionTest {

    @Test
    public void checkIfTokenIsValid() {
        String exampleToken = TokenUtils.createToken("Tom", "123456");

        assertTrue(TokenUtils.isNotExpired(exampleToken));
    }

    @Test
    public void checkIfTokeIsValidAfter5Minutes() throws InterruptedException {
        String exampleToken = TokenUtils.createToken("Tom", "123456");
        Thread.sleep(300000);
        assertFalse(TokenUtils.isNotExpired(exampleToken));
    }
}
