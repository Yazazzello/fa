package nl.funda.yazazzello.fa.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestTools {

    public static String readFile(String name) {
        InputStream stream = TestTools.class.getClassLoader().getResourceAsStream(name);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }
}
