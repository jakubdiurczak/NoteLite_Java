package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String PATH_TO_DATABASE = "jdbc:sqlite:note.db";
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    private static final String FIRST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_CONTENT + " TEXT);";

    private static int rowIndex = (-1);

    private static void executeCommand(String sql) {
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

    public static void initiateDatabase(){
        executeCommand(FIRST_TABLE);
    }

    public static void removeNote(){
        if(rowIndex > 0) {
            executeCommand("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + rowIndex + ";");
            rowIndex = (-1);
        }
    }

    public static void addNote(String fieldText, String areaText){
        if(rowIndex > 0) {
            executeCommand("UPDATE " + TABLE_NAME + " SET " +
                    COLUMN_TITLE + "='" + fieldText + "', " +
                    COLUMN_CONTENT + "='" + areaText + "' " +
                    "WHERE " + COLUMN_ID + "=" + rowIndex + ";");
        } else{
            executeCommand("INSERT INTO " + TABLE_NAME +
                    "(" + COLUMN_TITLE + ", " + COLUMN_CONTENT + ")" +
                    "VALUES('" + fieldText + "', '" + areaText + "');");
        }
    }

    public static List<String> getNoteList() {
        List<String> noteList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(PATH_TO_DATABASE);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + COLUMN_ID + ", " + COLUMN_TITLE +
                    " FROM " + TABLE_NAME + ";");
            while (rs.next()) {
                noteList.add(rs.getString(COLUMN_ID) + ": " + rs.getString(COLUMN_TITLE));
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return noteList;
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

    public static void setRowIndex(int index){
        rowIndex = index;
    }

}
