package expensemanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseClass {
    private int id;
    private LocalDate date;
    private double amount;
    private String description;
    private String category;

    public ExpenseClass(int id, LocalDate date, double amount, String description, String category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    /**
     * Serializes this expense as one CSV line. Any field containing a comma,
     * quote, or newline gets wrapped in quotes (standard CSV escaping), so a
     * description like "Lunch, coffee" no longer corrupts the file.
     */
    @Override
    public String toString() {
        return id + "," + date + "," + amount + "," + escape(description) + "," + escape(category);
    }

    public String toDisplayString() {
        return id + ".  " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "  " + amount + "  " + description + "  " + category;
    }

    private static String escape(String field) {
        if (field == null) return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}