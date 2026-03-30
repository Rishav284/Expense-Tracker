package ExpenseManger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExpenseFunctions {
    static void CreatingFile(){
        //Creating the database or txt file
        File obj=new File("StoreData.txt");
        try {
            obj.createNewFile();
//            BufferedWriter fw=new BufferedWriter(new FileWriter("StoreData.txt"));
//            fw.write("S.No  "+"DATE   "+ "AMOUNT  "+"DESCRIPTION    "+"CATEGORY   ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AddExpense(String date, double amt,String description, String category){
        //Ensures that the database exists or txt file exists
        if(!(new File("StoreData.txt").exists())) CreatingFile();
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter("StoreData.txt",true));
            List<String> nLines= Files.readAllLines(Path.of("StoreData.txt"));
            int i=nLines.size();
            if(i==0) CreatingFile();
            if(i!=0)fw.newLine();
            fw.write(date+"  "+amt+"  "+description+"  "+category);
            fw.close();
            System.out.println("Successfully Added!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void UpdateExpense(String date, double oldAmt, double newAmt,String category){
        Path path= Paths.get("StoreData.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String[] wordsArr=lines.get(i).trim().split("\\s+");
                List<String> inLine=new ArrayList<>(Arrays.asList(wordsArr));
                if (inLine.contains(date) && inLine.contains(String.valueOf(oldAmt)) && inLine.contains(category) ) {
                    inLine.set(1,""+newAmt); // Update the line in the list
                    lines.set(i,String.join("  ",inLine));
                }
            }
            Files.write(path,lines);
            System.out.println("Updated Successfully!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void DeleteExpense(String date, double amt,String category){
        Path path= Paths.get("StoreData.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String[] wordsArr=lines.get(i).trim().split("\\s+");
                List<String> inLine=new ArrayList<>(Arrays.asList(wordsArr));
                if (inLine.contains(date) && inLine.contains(String.valueOf(amt)) && inLine.contains(category) ) {
                    lines.remove(i);
                }
            }
            Files.write(path,lines);
            System.out.println("Deleted Successfully!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void View(){
        System.out.println("List of Your Expenses:");
        Path path= Paths.get("StoreData.txt");
        try {
            BufferedReader br=new BufferedReader(new FileReader("StoreData.txt"));
            br.lines();
            int i=1;
            String line;
            System.out.println("S.No  "+"DATE   "+ "AMOUNT  "+"DESCRIPTION    "+"CATEGORY   ");
            while((line=br.readLine())!=null){
                System.out.println((i++)+".   "+line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void Select(String date, double amt,String category){
        Path path= Paths.get("StoreData.txt");
        int j=1;
        try {
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String[] wordsArr=lines.get(i).trim().split("\\s+");
                List<String> inLine=new ArrayList<>(Arrays.asList(wordsArr));
                if (inLine.contains(date) && inLine.contains(String.valueOf(amt)) && inLine.contains(category) ) {
                    System.out.println((j++)+".  "+lines.get(i));
                }
            }
            System.out.println("Here is Your Selected Data!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void MonthlySummary(String Month){
        double sMonth=0;

        String s=Month(Month);
        if(s==null){
            System.out.println("Invalid Month!! Please enter correct month");
            return;
        }
        Path path= Paths.get("StoreData.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String[] wordsArr=lines.get(i).trim().split("\\s+");
                List<String> inLine=new ArrayList<>(Arrays.asList(wordsArr));
                if (inLine.get(0).contains(s) ) {
                    sMonth+=Double.parseDouble(inLine.get(1));
                }
            }
            System.out.println("Your "+Month+" total Expense: "+sMonth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static String Month(String month){
        month=month.toLowerCase();
        switch(month){
            case "jan":
            case "january":
                return "/01/";
            case "feb":
            case "february":
                return "/02/";
            case "march":
                return "/03/";
            case "april":
                return "/04/";
            case "may":
                return "/05/";
            case "june":
                return "/06";
            case "july":
                return "/07/";
            case "aug":
            case "august":
                return "/08/";
            case "sep":
            case "sept":
            case "september":
                return "/09/";
            case "oct":
            case "october":
                return "/10/";
            case "nov":
            case "november":
                return "/11/";
            case "dec":
            case "december":
                return "/12/";
        }
        return null;
    }
}
