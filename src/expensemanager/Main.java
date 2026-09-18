package expensemanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Expense Tracker
        Scanner in=new Scanner(System.in);
        System.out.print("Enter UserName:");
        String name=in.nextLine();
        ExpenseFunctions a = new ExpenseFunctions();
        a.setUserName(name);
        String cont="YES";
        while (cont.equalsIgnoreCase("yes")){
            System.out.print("1. Add Expense\n2. Update Expense\n3. Delete Expense\n4. View Expenses \n5. Total Expenditure of Specific Month \n6. View a specific expense\nPick Your Choice From above- ");
            int choice = readInt(in);
            switch (choice) {
                case 1:
                    addExpense(in,a);
                    break;
                case 2:
                    updateExpense(in,a);
                    break;
                case 3:
                    deleteExpense(in,a);
                    break;
                case 4:
                    a.viewExpense();
                    break;
                case 5:
                    System.out.print("Enter Year(e.g. 2026):");
                    int year = readInt(in);
                    System.out.print("Select Month(1-12):");
                    int month = readInt(in);
                    a.monthlySummary(year, month);
                    break;
                case 6:
                    selectExpense(in,a);
                    break;
                default:
                    System.out.println("Please Enter a valid choice from the above");
            }
            System.out.print("Do you Want to continue(YES/NO):");
            cont=in.nextLine();
        }
    }


    static void addExpense(Scanner in, ExpenseFunctions a){
        System.out.print("Add Expense:- \nAdd amount-");
        double amount = readDouble(in);
        System.out.print("Add Description-");
        String description = in.nextLine();
        System.out.print("Enter expense Category(food, beverage, stationary...)-");
        String category=in.nextLine();
        System.out.print("Enter Date(dd/mm/yyyy), or leave blank for today-");
        String date = in.nextLine();
        a.addExpense(date, amount, description, category);
    }
    static void updateExpense(Scanner in,ExpenseFunctions a){
        System.out.println("Update Expense:-");
        System.out.print("Enter the ID of the expense to update (see 'View Expenses')-");
        int id = readInt(in);
        System.out.print("Enter new amount-");
        double newAmount = readDouble(in);
        a.updateExpense(id, newAmount);
    }
    static void deleteExpense(Scanner in,ExpenseFunctions a){
        System.out.print("Delete Expense:- \nEnter the ID of the expense to delete (see 'View Expenses')-");
        int id = readInt(in);
        a.deleteExpense(id);
    }
    static void selectExpense(Scanner in,ExpenseFunctions a){
        System.out.print("Enter the ID of the expense to view-");
        int id = readInt(in);
        a.selectExpense(id);
    }

    // Reads an integer from the user, re-prompting on bad input instead of
    // crashing the program with an uncaught InputMismatchException (what the
    // original in.nextInt() calls would do if someone typed text).
    static int readInt(Scanner in){
        while (true) {
            String line = in.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid whole number-");
            }
        }
    }

    // Same idea, for decimal amounts.
    static double readDouble(Scanner in){
        while (true) {
            String line = in.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number-");
            }
        }
    }
}