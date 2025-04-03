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


### ➕ Adding an Expense: `/add` or `/recurring`

#### General Expense

Adds a new expense to your list.

**Format**:
```
/add <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] [/d <DATE>]
```

- `<AMOUNT>` must be a positive number.
- `/c` and `<CATEGORY>` are optional. Default is `Uncategorized`.
- `/d` and `<DATE>` are optional. Default is today's `date`

#### Extra: Recurring Expense

Adds a new recurring expense to the list 

**Format**:
```
/recurring <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] /d <DATE>
```
* **New input:** `/d` and `<DATE>` must be in the input
* This `<DATE>` is when it is added automatically to the general list

**Example**:
```
/add Coffee $5.50 /c Food /d 03-03-2025
/recurring Coffee $5.50 /c Food /d 04-03-2025
```

---

### ❌ Deleting an Expense: `/delete` or `/delete-recurring`

#### General Expense

Removes an expense by its number in the list.

**Format**:
```
/delete <EXPENSE_NUMBER>
```
#### Extra: Recurring Expense

Adds a new recurring expense to the list

**Format**:
```
/delete-recurring <RECURRING_EXPENSE_NUMBER>
```
* **New change:** no change

**Example**:
```
/delete 2
/delete-recurring 2
```

---
### ✏️ Editing an Expense: `/edit` or `/edit-recurring`

Edits an existing expense's description, amount, category, or date. This allows you to fix mistakes or update details of a previously recorded expense.

#### General Expenses
**Format**:
```
/edit <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]
```

- `<INDEX>` is the 1-based position of the expense in the list (e.g., from `/list`).
- `/d`, `/$`, `/c`, and `/dt` are optional flags to update the description, amount, category, and date, respectively, but requires at least one optional flag.
- If a field is omitted, the original value will be retained.

**Example**:
```
/edit 2 /d Dinner /$ 25.00 /c Dining /dt 25-12-2024
```
#### Extra: Recurring Expenses
**Format**:
```
/edit-recurring <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]
```

- `<INDEX>` is the 1-based position of the expense in the list (e.g., from `/list`).
- `/d`, `/$`, `/c`, and `/dt` are optional flags to update the description, amount, category, and date, respectively, but requires at least one optional flag.
- If a field is omitted, the original value will be retained.

**Example**:
```
/edit-recurring 2 /d Dinner /$ 25.00 /c Dining /dt 25-12-2024
```

This edits the second expense in the list with the new description "Dinner", amount `$25.00`, category "Dining", and date `25-12-2024`.

---
### 📋 Listing Expenses: `/list` or `/list-recurring`

#### General Expense

Displays all recorded expenses.

**Format**:
```
/list
```

#### Extra: Recurring Expense 

Displays all recorded recurring expenses

**Format**:
```
/list-recurring
```

---

### 🧮 Calculating Total Expenses: `/total` or `/total-recurring`

#### General Expense

Shows the total amount spent.

**Format**:
```
/total
```

#### Extra: Recurring Expense

Shows the total amount in recurring expenses.

**Format**:
```
/total-recurring
```

---
### 📊 Calculating Average Expense: `/average` or `/average-recurring`

#### General Expense

Displays the average amount spent per recorded expense.

**Format**:
```
/average
```

#### Extra: Recurring Expense

Displays the average amount spent per recorded recurring expense.

**Format**:
```
/average-recurring
```

---


### 💲Setting Monthly Budgets: `/budget`
Sets a monthly budget limit, and warnings will be generated when the
budget limit is exceeded or almost exceeded (10% short of being exceeded).

**Format**
```
/budget $<AMOUNT>
```
- `<AMOUNT>` must be a positive number greater than 0.

**Example:**
```
/budget $500
```

---

### 📝 Getting Summary: `/summary` or `/summary-recurring`

#### General Expenses

Shows a brief summary of the current expenses. This includes
the spending per category, the category which contributes to the
highest spending, and the grand total.

**Format:**
```
/summary
```

#### Extra: Recurring Expenses

**Format:**
```
/summary-recurring
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
/help <COMMAND>
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

**A**: Install the app in another computer and simply overwrite the empty `data.txt` save file
it creates with the `data.txt`save file that contains all your previous data.

---

## Command Summary

Here’s a quick cheat sheet of commands:

| Command             | Format                                                    | Description                                                              |
|---------------------|-----------------------------------------------------------|--------------------------------------------------------------------------|
| Add Expense         | `/add <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] [/d <DATE>]` | Adds a new expense                                                       |
| Delete Expense      | `/delete <EXPENSE_NUMBER>`                                | Deletes an expense by its index                                          |
| Edit Expenses       | `/edit <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]`| Edits an existing expense                             |
| List Expenses       | `/list`                                                   | Lists all expenses                                                       |
| Total Expense       | `/total`                                                  | Shows total expenses                                                     |
| Average Expense     | `/average`                                                | Shows average expense                                                    |
| Set Monthly Budget  | `/budget $<AMOUNT>`                                       | Sets monthly budgets, and generate warnings if almost exceeded / exceeded |
| Summary of Expenses | `/summary`                                                | Gives a brief summary of the expenses in the list                     |
| Help                | `/help <COMMAND>`                                         | Shows help for all or specific cmd                                       |

### Recurring Expenses

| Command | Format                                                         | Description                                                 |
|---------|----------------------------------------------------------------|-------------------------------------------------------------|
| Add Expense | `/recurring <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] /d <DATE>` | Adds a new recurring expense to be added at stipulated date |
| Delete Expense | `/delete-recurring <EXPENSE_NUMBER>`                           | Deletes a recurring expense by its index                    |
| Edit Expenses  | `/edit <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]`| Edits a recurring expense                                   |
| List Expenses | `/list-recurring`                                              | Lists all recurring expenses                                |
| Total Expense | `/total-recurring`                                             | Shows total recurring expenses                              |
| Average Expense | `/average-recurring`                                           | Shows average recurring expense                             |
| Summary of Expenses | `/summary-recurring`                                           | Gives a brief summary of the recurring expenses in the list |



---

