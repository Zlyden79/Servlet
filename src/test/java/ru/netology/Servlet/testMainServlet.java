package ru.netology.Servlet;

import org.junit.jupiter.api.Test;
import ru.netology.servlet.MainServlet;

public class testMainServlet {
    @Test
    void testGetIdFromPath(){

        String path = "/api/posts/248";
        MainServlet ms = new MainServlet();
        long id = ms.getIdFromPath(path);
        System.out.println(id);
    }

}
