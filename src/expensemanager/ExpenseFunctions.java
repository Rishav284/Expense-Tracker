package expensemanager;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;


public class ExpenseFunctions {
    private String userName;
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public void setUserName(String userName) {
        // Strip anything that isn't a letter/number/underscore so the name
        // is always safe to use as a filename (e.g. "../../etc" can't escape
        // the data/ folder, and spaces/punctuation won't break file paths).
        String cleaned = userName.trim().toLowerCase().replaceAll("[^a-z0-9_]", "");
        this.userName = cleaned.isEmpty() ? "user" : cleaned;
    }

    private void createFile(){
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File obj=new File("data/"+userName+".csv");
        try {
            obj.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<ExpenseClass> loadExpenses() {
        ArrayList<ExpenseClass> list = new ArrayList<>();
        File file = new File("data/"+userName+".csv");
        if (!file.exists()) {
            return list;
        }

        // try-with-resources: the reader is guaranteed to close even if an
        // exception is thrown mid-read (the original version could leak the
        // file handle if parsing failed partway through).
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = parseCsvLine(line);
                int id = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String description = parts[3];
                String category = parts[4];
                list.add(new ExpenseClass(id, date, amount, description, category));
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return list;
    }

    private void saveExpenses(ArrayList<ExpenseClass> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/"+userName+".csv"))) {
            for (ExpenseClass e : list) {
                bw.write(e.toString());
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error saving file");
        }
    }

    /**
     * Splits one CSV line back into fields, respecting quoted fields so a
     * comma inside a quoted description isn't mistaken for a field
     * separator. Mirrors the escaping done in ExpenseClass.toString().
     */
    private static String[] parseCsvLine(String line) {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"'); // escaped quote ("" -> ")
                    i++;
                } else if (c == '"') {
                    inQuotes = false;
                } else {
                    current.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    /**
     * Adds a new expense. dateStr may be blank (defaults to today) or a
     * dd/MM/yyyy date, so you can log an expense from an earlier day.
     */
    public void addExpense(String dateStr, double amt, String description, String category){
        if(!(new File("data/"+userName+".csv").exists())) createFile();
        if(amt<=0) {
            System.out.println("Please Enter Valid Amount!!!");
            return;
        }
        if(category==null || category.isBlank()) {
            System.out.println("Please Enter Category of the Item!!!");
            return;
        }

        LocalDate date;
        if (dateStr == null || dateStr.isBlank()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateStr, F);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date");
                return;
            }
        }

        ArrayList<ExpenseClass> list = loadExpenses();
        int nextId = list.stream().mapToInt(ExpenseClass::getId).max().orElse(0) + 1;
        list.add(new ExpenseClass(nextId, date, amt, description, category));
        saveExpenses(list);
        System.out.println("Successfully Added!!! (ID: " + nextId + ")");
    }

    // Update and delete now key off the expense's unique ID instead of
    // matching (date, amount, category) together, which used to silently
    // grab the wrong entry whenever two expenses shared all three values.
    public void updateExpense(int id, double newAmt){
        if(newAmt<=0){
            System.out.println("Please Enter Valid Amount!!!");
            return;
        }
        ArrayList<ExpenseClass> list=loadExpenses();
        for (ExpenseClass e : list) {
            if (e.getId() == id) {
                e.setAmount(newAmt);
                saveExpenses(list);
                System.out.println("Updated Successfully!!!");
                return;
            }
        }
        System.out.println("No expense found with ID " + id);
    }

    public void deleteExpense(int id){
        ArrayList<ExpenseClass> list=loadExpenses();
        boolean removed = list.removeIf(e -> e.getId() == id);
        if(removed){
            saveExpenses(list);
            System.out.println("Deleted Successfully!!!");
        } else {
            System.out.println("No expense found with ID " + id);
        }
    }

    public void viewExpense(){
        ArrayList<ExpenseClass> list=loadExpenses();
        if (list.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("List of Your Expenses:");
        for (ExpenseClass e : list) {
            System.out.println(e.toDisplayString());
        }
    }

    public void selectExpense(int id){
        ArrayList<ExpenseClass> list=loadExpenses();
        for (ExpenseClass e : list) {
            if (e.getId() == id) {
                System.out.println(e.toDisplayString());
                return;
            }
        }
        System.out.println("No expense found with ID " + id);
    }

    // Now takes a year too, so January 2025 and January 2026 are no longer
    // summed together as if they were the same month.
    public void monthlySummary(int year, int month){
        if(month<1 || month>12){
            System.out.println("Invalid Month!! Please enter correct month");
            return;
        }
        double totalSum=0;
        ArrayList<ExpenseClass> list=loadExpenses();
        for (ExpenseClass e : list) {
            if (e.getDate().getMonthValue() == month && e.getDate().getYear() == year) {
                totalSum += e.getAmount();
            }
        }
        System.out.println("Your total expense for " + Month.of(month) + " " + year + ": " + totalSum);
    }
}