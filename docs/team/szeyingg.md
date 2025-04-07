# Sze Ying - Project Portfolio Page

## Overview
**FinTrek** is a simple and intuitive Command Line Interface (CLI) tool that helps users track their personal expenses.
Users can quickly add, view, analyze, and manage their expenses using short commands.

---
## Summary of Contributions

#### - Code contributed: [Link to tP code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21T00%3A00%3A00&tabOpen=true&tabType=authorship&tabAuthor=szeyingg&tabRepo=AY2425S2-CS2113-F12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented
#### 📌 New Features
1. **Add Command:** allows users to add new expenses to the list
2. **List Sort Command**: allows users to sort expense list as desired
3. **Add Category Command**: allows users to add custom categories
4. **List Category Command**: allows users to see list of default & custom categories

#### 📝 JUnit tests
- Added JUnit test code for Add, Delete, Average, & Sort Commands
- Added Junit test code for Category related commands: Add & List Category

#### ✨ Highlights
- Introduced `TestUtils` helper functions to streamline JUnit assertions, making test code more concise and readable.
- Developed the idea for `CategoryManager` while debugging `AddCommand`:
  - **Bug:** Dates were mistakenly interpreted as categories for certain user inputs
  - **Solution:** Introduced centralized list of valid categories to improve input validation
  - **Added benefit:** Enabled users to create custom categories to allow for more personalized expense tracking
- Refined the input parsing process using `Pattern` and `Matcher`, enabling stricter and more reliable command validation.


### Contributions to User Guide
- **Added descriptions and examples:** SortCommand, AddCategoryCommand, and ListCategoryCommand

### Contributions to Developer Guide
- Created reference frames to simplify originally complicated diagrams.
- **Updated UML sequence diagrams:** Add, AddCategory, Budget, List, ListSort Commands.
- **Explained execution:** ListSort, AddCategory, ListCategory, and Budget Commands.


### Team-based contributions
- Created GitHub team repo
- Peer PR Reviews: [#70](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/70), 
                   [#190](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/190)

### Contributions beyond project team
- Performed rigorous testing to report over 20 bugs for another team during [PE Dry Run](https://github.com/szeyingg/ped/issues)