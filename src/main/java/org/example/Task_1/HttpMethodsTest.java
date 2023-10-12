package org.example.Task_1;

import java.io.IOException;

public class HttpMethodsTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpMethods methods = new HttpMethods();
        methods.getToDos(4);
    }
}
