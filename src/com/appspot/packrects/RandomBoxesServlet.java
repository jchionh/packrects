package com.appspot.packrects;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import com.appspot.packrects.engine.BasicRect;
import com.appspot.packrects.engine.Packer;
import com.appspot.packrects.engine.RectData;

@SuppressWarnings("serial")
public class RandomBoxesServlet extends HttpServlet {
    
    private static final int NUM_BOXES = 25;
    private static final int MAX_EXTENT = 15;
    private static final int SCALE = 10;
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        // generate random box data here
        int numBoxes = 1 + (int) (Math.floor(Math.random() * NUM_BOXES));
        List<RectData> dataSet = new LinkedList<RectData>();
        for (int i = 0; i < numBoxes; ++i) {
            int width = ((int) (Math.floor(Math.random() * MAX_EXTENT)) + 1) * SCALE;
            int height = ((int) (Math.floor(Math.random() * MAX_EXTENT)) + 1) * SCALE;
            RectData data = new RectData(width, height, i);
            dataSet.add(data);
        }
        
        Packer packer = new Packer();
        packer.setDataSet(dataSet);
        BasicRect[] result = packer.pack();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        
        resp.setContentType("text/plain");
        //resp.getWriter().println("num boxes: " + result.length + "\n" + json);
        resp.getWriter().println(json);
    }
}
