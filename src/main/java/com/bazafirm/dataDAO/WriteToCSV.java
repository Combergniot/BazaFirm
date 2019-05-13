package com.bazafirm.dataDAO;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriteToCSV {

    //    Settings
    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/bazafirm?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String USER = "root";
    private final static String PASSWORD = "root";
    private final static String QUERY = "select * from company";
    private final static String PATH = "C:\\Baza ofert\\bazafirm.csv";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";


    public static void createCSV() {
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
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



