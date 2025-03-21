# User Guide

## Introduction

**FinTrek** is a simple and intuitive Command Line Interface (CLI) tool that helps users track their personal expenses. Users can quickly add, view, analyze, and manage their expenses using short commands. It is designed for speed, simplicity, and ease of use.

---

## Quick Start

1. Ensure that you have **Java 17** or above installed on your computer.
2. Download the latest release v1.0 `tp.jar`.
3. Run on command line using:
   `java -jar tp.jar`
---

## Features

### ➕ Adding an Expense: `/add`

Adds a new expense to your list.

**Format**:
```
/add <description> $<amount> /c <category>
```

- `<amount>` must be a positive number.
- `/c` and `<category>` are optional. Default is `Uncategorized`.

**Example**:
```
/add Coffee $5.50 /c Food
```

---

### ❌ Deleting an Expense: `/delete`

Removes an expense by its number in the list.

**Format**:
```
/delete <expense_number>
```

**Example**:
```
/delete 2
```

---

### 📋 Listing Expenses: `/list`

Displays all recorded expenses.

**Format**:
```
/list
```

---

### 🧮 Calculating Total Expenses: `/total`

Shows the total amount spent.

**Format**:
```
/total
```

---

### 📊 Calculating Average Expense: `/average`

Displays the average amount spent per recorded expense.

**Format**:
```
/average
```

---

### 🆘 Getting Help: `/help`

Shows help messages for commands.

**Format (general help)**:
```
/help
```

**Format (specific command)**:
```
/help <command>
```

**Example**:
```
/help add
```

---

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: FinTrek currently does not persist data across sessions. All data is stored in memory during runtime and is lost once the program exits.

---

## Command Summary

Here’s a quick cheat sheet of commands:

| Command        | Format                                       | Description                        |
|----------------|----------------------------------------------|------------------------------------|
| Add Expense    | `/add <description> $<amount> /c <category>` | Adds a new expense                 |
| Delete Expense | `/delete <expense_number>`                   | Deletes an expense by its index    |
| List Expenses  | `/list`                                      | Lists all expenses                 |
| Total Expense  | `/total`                                     | Shows total expenses               |
| Average Expense| `/average`                                   | Shows average expense              |
| Help           | `/help [command]`                            | Shows help for all or specific cmd |

---

