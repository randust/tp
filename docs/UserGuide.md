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

## Notes about the command format:

>- Words in `UPPER_CASE` are the parameters which are expected from the user,
e.g. in `/add <DESCRIPTION> $<AMOUNT>`, `DESCRIPTION` is a parameter which can be input as `/add Food`.
>- Items in square brackets are optional. e.g `/add <DESCRIPTION> $<AMOUNT> [/c<CATEGORY>] [/d<DATE>]` can be instantiated as `/add 
/Coffee $5.00 /c Beverages` or as `/add Coffee $5.00`.
>- Extraneous parameters for commands that do not take in parameters (such as `help`, `list` or `total`) will be ignored, 
e.g. if the command specifies `help 2113`, it will be interpreted as `help`.


### ➕ Adding an Expense: `/add`

Adds a new expense to your list.

**Format**:
```
/add <description> $<amount> [/c <category>] [/d <date>]
```

- `<amount>` must be a positive number.
- `/c` and `<category>` are optional. Default is `Uncategorized`.
- `/d` and `<date>` are optional. Default is today's `date`

**Example**:
```
/add Coffee $5.50 /c Food /d 03-03-2025
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


### 💲Setting Monthly Budgets: `/budget`
Sets a monthly budget limit, and warnings will be generated when the
budget limit is exceeded or almost exceeded (10% short of being exceeded).

**Format**
```
/budget $<amount>
```
- `amount` must be a positive number greater than 0.

**Example:**
```
/budget $500
```

---

### 📝 Getting Summary: `/summary`
Shows a brief summary of the current expenses. This includes
the spending per category, the category which contributes to the
highest spending, and the grand total.

**Format:**
```
/summary
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

### 👋 Exiting the program: `bye`

Exits the program.

**Format: `bye`**

---

### ✅ Saving the data
FinTrek saves data of your expenses automatically in the hard disk
using a save file called `data.txt`. There is no need to save manually.

---

### ✏️ Editing the data file
FinTrek data are saved automatically inside a local `data.txt` file.
Advanced users who know what they are doing are welcome to update
their list of expenses directly by editing the txt file.

> #### ❗CAUTION
> - If your changes to one of the lines in the data file makes its format invalid, that line will be skipped
>by FinTrek, and it will notify you on the Command Line.
> - Hence, that particular expense will not be included in your list of expenses.
> - Only update the data directly by editing the data file if you are
> confident that you are making the right changes, following the 
> correct format.


## FAQ

**Q**: How do I transfer my data to another computer?

**A**: FinTrek currently does not persist data across sessions. All data is stored in memory during runtime and is lost once the program exits.

---

## Command Summary

Here’s a quick cheat sheet of commands:

| Command             | Format                                                 | Description                                                              |
|---------------------|--------------------------------------------------------|--------------------------------------------------------------------------|
| Add Expense         | `/add <description> $<amount> /c <category> /d <date>` | Adds a new expense                                                       |
| Delete Expense      | `/delete <expense_number>`                             | Deletes an expense by its index                                          |
| List Expenses       | `/list`                                                | Lists all expenses                                                       |
| Total Expense       | `/total`                                               | Shows total expenses                                                     |
| Average Expense     | `/average`                                             | Shows average expense                                                    |
| Set Monthly Budget  | `/budget $<amount>`                                    | Sets monthly budgets, and generate warnings if almost exceeded / exceeded |
| Summary of Expenses | `/summary`                                             | Gives a brief summary of the expenses in the list                     |
| Help                | `/help [command]`                                      | Shows help for all or specific cmd                                       |

---

