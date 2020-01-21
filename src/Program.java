import java.sql.*;
import java.util.Scanner;

public class Program{
    public static void main(String ... args) {
        Program program = new Program();
        if(program.open()) {
            System.out.println("Hello and welcome to student data base");
            program.menu();
            program.close();
        }
    }
    Connection co;
    boolean open(){
        try {
            Class.forName("org.sqlite.JDBC");
            co = DriverManager.getConnection("jdbc:sqlite:student.db");
            System.out.println("Connected");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    void insert_student(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter student group name: ");
            int group_id = scanner.nextInt();

            String query = "INSERT INTO student (name, group_id) " +
                    "VALUES ('"+ name +"','"+ group_id +"')";

            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    statement.executeUpdate(query);
                    System.out.print("Student added\n");
                    statement.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void insert_group(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter group name: ");
            int name = scanner.nextInt();
            String query = "INSERT INTO groups (name) " +
                    "VALUES ('"+ name +"')";
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    statement.executeUpdate(query);
                    System.out.print("Group added\n");
                    statement.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void select_students(){
        try{
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    String query = "SELECT * FROM student ORDER BY name";
                    ResultSet rs = statement.executeQuery(query);
                    while(rs.next()){
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        int group_id = rs.getInt("group_id");
                        System.out.println(id+"\t|"+name+"\t|"+group_id);
                    }
                    rs.close();
                    statement.close();
                    System.out.println("\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void select_students_from_group(){
        try{
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter group name: ");
                    int group = scanner.nextInt();
                    String query = "SELECT * FROM student WHERE group_id = " +group+ " ORDER BY name";
                    ResultSet rs = statement.executeQuery(query);
                    while(rs.next()){
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        int group_id = rs.getInt("group_id");
                        System.out.println(id+"\t|"+name+"\t|"+group_id);
                    }
                    rs.close();
                    statement.close();
                    System.out.println("\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void select_groups(){
        try{
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    String query = "SELECT * FROM groups ORDER BY name";
                    ResultSet rs = statement.executeQuery(query);
                    while(rs.next()){
                        int id = rs.getInt("id");
                        int name = rs.getInt("name");
                        System.out.println(id+"\t|"+name);
                    }
                    rs.close();
                    statement.close();
                    System.out.println("\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void change_students(){
        try{
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();

                    String query = "SELECT * FROM student ";
                    ResultSet rs = statement.executeQuery(query);
                    int studentsGroup_id=0;
                    while(rs.next()){
                        if(rs.getString("name").equals(name)){
                            studentsGroup_id=rs.getInt("group_id");
                            System.out.print("Enter student new name: ");
                            String new_name = scanner.nextLine();
                            String query1 = "DELETE FROM student WHERE name = '" + name + "'";
                            String query2 = "INSERT INTO student (name, group_id) " +
                                    "VALUES ('"+ new_name +"','"+ studentsGroup_id +"')";
                            statement.executeUpdate(query1);
                            statement.executeUpdate(query2);
                            System.out.println("Student renamed\n");
                        }
                    }
                    rs.close();
                    statement.close();
                    System.out.println("\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void change_groups(){
        try{
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter group name: ");
                    int name = scanner.nextInt();
                    System.out.print("Enter group new name: ");
                    int new_name = scanner.nextInt();
                    String query1 = "DELETE FROM groups WHERE name = '" + name + "'";
                    String query2 = "INSERT INTO groups (name) " +
                            "VALUES ('"+ new_name +"')";
                    statement.executeUpdate(query1);
                    statement.executeUpdate(query2);
                    System.out.println("Group renamed");
                    statement.close();
                    System.out.println("\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void delete_student(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            String query = "DELETE FROM student WHERE name = '" + name + "'";
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    statement.executeUpdate(query);
                    System.out.print("Student deleted\n");
                    statement.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void delete_group(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter group name: ");
            String name = scanner.nextLine();
            String query = "DELETE FROM groups WHERE name = '" + name + "'";
            String query1 = "DELETE FROM student WHERE group_id = '" + name + "'";
            if(co != null) {
                Statement statement = co.createStatement();
                if (statement != null) {
                    statement.executeUpdate(query);
                    statement.executeUpdate(query1);
                    System.out.print("Group and all corresponding students deleted\n");
                    statement.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void close(){
        try{
            if(co != null) {
                co.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void menu(){
        System.out.println("\nEnter 1 to show all the students\n" +
                "Enter 2 to show all the students from a certain group\n" +
                "Enter 3 to show all the groups\n" +
                "Enter 4 to add a student\n" +
                "Enter 5 to add a group\n" +
                "Enter 6 to rename the student\n" +
                "Enter 7 to rename the group\n" +
                "Enter 8 to delete student\n" +
                "Enter 9 to delete group\n" +
                "Enter 0 to quit\n");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice){
            case(1):
                select_students();
                menu();
            case(2):
                select_students_from_group();
                menu();
            case(3):
                select_groups();
                menu();
            case(4):
                insert_student();
                menu();
            case(5):
                insert_group();
                menu();
            case(6):
                change_students();
                menu();
            case(7):
                change_groups();
                menu();
            case(8):
                delete_student();
                menu();
            case(9):
                delete_group();
                menu();
            case(0):
                System.out.println("Good bye");
                break;
        }
    }
}