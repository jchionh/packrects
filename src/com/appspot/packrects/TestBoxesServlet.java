package com.appspot.packrects;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class TestBoxesServlet extends HttpServlet {
    
    private static final String PATH_TO_DATA = "/data/boxes/testboxes.txt";    		
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        InputStream is = getServletContext().getResourceAsStream(PATH_TO_DATA);
        Scanner scanner = null;
        scanner = new Scanner(is);
        StringBuilder sb = new StringBuilder();
        
        try {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line);
            }    
        } finally {
            scanner.close();
        }
        
        resp.setContentType("text/plain");
        resp.getWriter().println(sb.toString());
    }
}
