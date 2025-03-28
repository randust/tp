# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


## Product scope
### Target user profile

University Students

We would like to help students to manage their money wisely through creating a financial chatbot: FinTrek.
Hopefully, students will be more financially conscious and able to manage their budget wisely. 

### Value proposition

1. Provide easy access to track and record expenses
2. Reduce the hassle of manually calculating expenses 
3. Generate summaries and trends for user spending
4. Provide financial awareness to users
5. Simplify the process of splitting expenses amongst friends 
6. Helpful for students to manage their budget
7. More secure and portable than physical records

## User Stories

| Version | As a ...           | I want to ...                                                                         | So that I can ...                                                  |
|---------|--------------------|---------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| v1.0    | new user           | ask the bot for a guide on the commands                                               | know how to use the application                                    |
| v1.0    | student            | add an expense into the list of expenses                                              | keep track of my expenses                                          |
| v1.0    | student            | delete an expense from the list of expenses                                           | remove something I wrongly listed                                  |
| v1.0    | lazy student       | automatically calculate statistics pertaining to my expenses                          | save a lot of time by not counting manually                        |
| v2.0    | student            | edit the details of an expense from the current list of expenses                      | have more flexibility to edit details in case I enter them wrongly |
| v2.0    | analytical student | get a summary of the total spendings for each category                                | know where most of my monthly budget goes to                       |
| v2.0    | lazy student       | set up recurring spendings or expenses                                                | save time by not adding them manually every month                  |
| v2.0    | meticulous student | sort my list of expenses based on dates, categories, or amount                        | better organize my list of expenses                                |
| v2.0    | forgetful student  | set up a budget limit or receive warnings when I will almost exceed my monthly budget | save money by not accidentally overspending                        |
| v2.0    | typical user       | save my list of expenses into a save file                                             | easily load them whenever I enter the application                  |
    

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing


### Parser

**Purpose**: To verify that user input is correctly interpreted and dispatched to the appropriate command.

##### Correct behavior

1. Launch the app using `FinTrek.main()`.
2. Type the following commands one at a time:

- `/add lunch $10 /c food`  
  → Expected: Expense is added with category `food`. Success message is printed.

- `/list`  
  → Expected: Displays a list containing the added expense.

- `/delete 1`  
  → Expected: Deletes the first expense. Success message with updated count is printed.

- `/total`  
  → Expected: Displays the total amount of all current expenses.

- `/average`  
  → Expected: Displays the average amount of all current expenses.

- `/help add`  
  → Expected: Displays detailed help message for the `/add` command.

##### Invalid command formats

Type the following commands one at a time:

- `add lunch $10`  
  → Expected: Error message — must start with '/'.

- `/blah blah`  
  → Expected: Error message — invalid command.

- *(Empty input or whitespace only)*  
  → Expected: Error message — must enter a command starting with '/'.

**Note:** All errors and successful command parsing are logged using the Java `Logger`.
