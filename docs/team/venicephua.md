# Venice Phua - Project Portfolio Page

## Overview
FinTrek is a simple and intuitive Command Line Interface (CLI) tool, designed to allow users to track and manage their 
expenses efficiently. Users can quickly add, view, analyze, and manage their expenses using short commands. 

### Summary of Contributions
#### Enhancements Implemented
- Feature: Added `Summary` command that allows users to view a brief summary of their expenses.
  - What it does: Displays either the overall summary with all categories and their total spending, or the summary of one specific category and the expenses in it. 
  - Justification: This feature allows users to monitor and draw conclusions about their spending patterns, allowing them to better manage their expenditure and keep within their budget.
  - Highlights: This feature was interesting to implement as I used HashMap to group the expenses by categories, instead of working with List which was used to manage the expenses.
- Feature: Added Total command to display grand total of expenditure.
- Feature: Contributed to Help command to display format instructions and examples for each command.
- JUnit Tests: Designed JUnit tests for `Help`, `List`, `Summary`, and `Total` commands 

#### Code Contributed:
[RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=venicephua&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=venicephua&tabRepo=AY2425S2-CS2113-F12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

#### Contributions to Documentation
  - User Guide: 
    - Added descriptions and examples for `Summary` Command.
    - Added a caution section about user input for users to take note when using FinTrek.
  - Developer Guide: 
    - Added Ui-Parser-CommandRegistry Component Class Diagram.
    - Added Ui-CommandRegistry Sequence Diagram.
    - Added `Add` Sequence Diagram.
    - Added `Summary` Execution Flow and Sequence Diagram.
    - Added `List All Category Totals`, and `List Single Category Total` Sequence Diagrams for `Summary`.

#### Contributions to team-based tasks
  - Set up team repo
  - Manage Issue Tracker (Issues [#51](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/51), [#52](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/52), [#53](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/53), [#54](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/54), [#55](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/55))
  - Enabled Java CI Workflows 
  - Reviewed PRs: [#41](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/41), [#98](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/98)
  - Refactor FinTrek class into FinTrekUi and FinTrek [#100](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/100)
