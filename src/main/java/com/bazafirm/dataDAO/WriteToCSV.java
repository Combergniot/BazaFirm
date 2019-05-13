package com.bazafirm.dataDAO;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WriteToCSV {

    private static final String FILE_NAME = "application.properties";
    private final static String CONNECTION_URL = "spring.datasource.url";
    private final static String USER = "spring.datasource.username";
    private final static String PASSWORD = "spring.datasource.password";
    private final static String DRIVER = "spring.datasource.driver-class-name";

    private final static String QUERY = "select * from company";
    private final static String PATH = "C:\\Baza ofert\\bazafirm.csv";

    private static Properties setUpProperties() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = WriteToCSV.class.getClassLoader().getResourceAsStream(FILE_NAME);
            if (input == null) {
                System.out.println("Sorry, unable to find " + FILE_NAME);
            }
            //load a properties file from class path, inside static method
            properties.load(input);
            //get the property value and print it out
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static void createCSV() {
        Properties properties = setUpProperties();
        try {
            Class.forName(properties.getProperty(DRIVER));
            Connection con = DriverManager.getConnection(
                    properties.getProperty(CONNECTION_URL),
                    properties.getProperty(USER),
                    properties.getProperty(PASSWORD));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            FileOutputStream fileStream = new FileOutputStream(new File(PATH));
            fileStream.write(0xef);
            fileStream.write(0xbb);
            fileStream.write(0xbf);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
            CSVWriter writer = new CSVWriter(outputStreamWriter);
            List<String[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new String[]
                        {rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("branch"),
                                rs.getString("region"),
                                rs.getString("street"),
                                rs.getString("postal_code"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getString("published_date"),
                                rs.getString("created_at"),
                                rs.getString("email"),
                        });
            }

            writer.writeAll(data);
            System.out.println("CSV written successfully.");
            writer.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}



