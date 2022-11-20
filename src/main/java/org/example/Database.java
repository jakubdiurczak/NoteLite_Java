package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    static final String PATH_TO_DATABASE = "jdbc:sqlite:note.db";
    static final String TABLE_NAME = "notes";
    static final String COLUMN_ID = "id";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_CONTENT = "content";

    static final String FIRST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_CONTENT + " TEXT);";

    static int rowIndex = (-1);

    public static void executeCommand(String sql) {
        try {
            Connection conn = java.sql.DriverManager.getConnection(PATH_TO_DATABASE);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> getNotesList() {
        List<String> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(PATH_TO_DATABASE);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_ID + ", " + COLUMN_TITLE +
                    " FROM " + TABLE_NAME + ";");
            while (rs.next()) {
                list.add(rs.getString(COLUMN_ID) + ": " + rs.getString(COLUMN_TITLE));
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    public static String getNoteContent(int selectedRow){
        String noteContent = null;
        try {
            Connection connection = DriverManager.getConnection(PATH_TO_DATABASE);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_CONTENT + " FROM " + TABLE_NAME
                    + " WHERE " + COLUMN_ID + " = " + selectedRow + ";");
            noteContent = rs.getString(COLUMN_CONTENT);
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return noteContent;
    }

}
