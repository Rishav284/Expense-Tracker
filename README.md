# 💰 Expense Tracker

A Java command-line application for tracking personal expenses — add, update, delete, and view spending, plus get monthly totals. Data is stored per-user in CSV files, so multiple people can use it on the same machine without their records mixing.

<!-- Add a screenshot or short GIF of a sample terminal session here -->

## Features

- **Add expenses** with amount, description, category, and date (defaults to today if left blank)
- **Update or delete** any expense by its unique ID
- **View all expenses** for the logged-in user
- **Monthly summaries** — total spend for a given month and year
- **Per-user storage** — each username gets its own CSV file under `data/`
- **Input validation** that re-prompts instead of crashing on bad input (invalid numbers, dates, empty fields)
- **CSV-safe** — descriptions or categories containing commas are stored and read back correctly

## Tech Stack

- **Java 17** (standard library only — no external dependencies)
- `java.time` (`LocalDate`) for date handling and parsing
- Plain file I/O (`BufferedReader`/`BufferedWriter`) for CSV persistence

## Getting Started

### Prerequisites
- JDK 17 or later installed ([adoptium.net](https://adoptium.net) if you need one)

### Clone and run
```bash
git clone https://github.com/Rishav284/ExpenseTracker.git
cd ExpenseTracker/src
javac expensemanager/*.java
java expensemanager.Main
```

Or open the project folder directly in IntelliJ IDEA and run `Main.java`.

## Usage

```
Enter UserName: alex
1. Add Expense
2. Update Expense
3. Delete Expense
4. View Expenses
5. Total Expenditure of Specific Month
6. View a specific expense
Pick Your Choice From above- 1
Add amount- 250
Add Description- Groceries
Enter expense Category(food, beverage, stationary...)- food
Enter Date(dd/mm/yyyy), or leave blank for today-
Successfully Added!!! (ID: 1)
Do you Want to continue(YES/NO): yes
```

## Project Structure

```
ExpenseTracker/
├── data/                        # per-user CSV files (generated at runtime, not committed)
└── src/expensemanager/
    ├── ExpenseClass.java         # Expense model: fields, CSV encoding/decoding
    ├── ExpenseFunctions.java     # Business logic: add/update/delete/view/summarize
    └── Main.java                 # CLI entry point and user prompts
```

## How Data Is Stored

Each user's expenses live in `data/<username>.csv`, one line per expense:
```
id,date,amount,description,category
1,2026-07-26,250.0,Groceries,food
2,2026-07-26,150.0,"Coffee, biscuit",food
```
Fields containing a comma are automatically wrapped in quotes, so free-text descriptions are safe to enter.

## Roadmap

- [ ] Migrate build to Maven for dependency management and easier setup
- [ ] Add JUnit test coverage for the business-logic layer
- [ ] Split persistence and business logic into separate classes (repository/service pattern)
- [ ] Optional: move from CSV to SQLite for more robust storage
- [ ] Optional: add a simple GUI (JavaFX) or REST API front end

## License

This project is available under the MIT License — add a `LICENSE` file to your repo to make this official.
