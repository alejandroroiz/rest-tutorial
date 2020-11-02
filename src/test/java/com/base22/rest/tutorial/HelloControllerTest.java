package com.base22.rest.tutorial;

import com.base22.rest.tutorial.web.HelloController;
import org.junit.Test;
import static org.junit.Assert.*;

public class HelloControllerTest {

    @Test
    public void testHelloController() {
        HelloController helloController = new HelloController();
        String result = helloController.index();
        assertEquals(result, "Hello Base22. This is a message from Spring!\n");
    }
}
