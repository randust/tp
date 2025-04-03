# Hung Ming Kin's Project Portfolio Page
## Project: FinTrek
FinTrek is a simple and intuitive Command Line Interface (CLI) tool that helps users track their personal expenses. Users can quickly add, view, analyze, and manage their expenses using short commands.

Given below are my contributions to the project.

- New Feature: Added delete command that allows user to delete expenses.
- New Feature: Added the ability to edit entered expenses.
    - What it does: allow the users to edit expenses by specifying the field(s) that he wants to edit, e.g., description, amount, etc.
    - Justification: This feature improves the product significantly because the user can make mistakes when typing in the expenses, and they won't need to re-enter the whole expense every time they make a mistake. The app should provide a convenient way to rectify them. 
    - Highlights: The implementation was challenging as it requires to give users maximum flexibility to change whichever the field they want. This involves complex regex pattern matching of the user input string.
    - Credits: ChatGPT teaches me the correct regex pattern matcher to accurately parse the user input.
- Code Contributed: [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=f12&sort=totalCommits%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=randust&tabRepo=AY2425S2-CS2113-F12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&since=2025-02-21)
- Contributions to the UG: edit command and CRUD commands.
- Contributions to the DG: overall architecture class diagram, expense component class diagram, delete command sequence diagram, edit command sequence diagram and instruction for manual testing for command router.
- Review/mentoring contributions: PR review comments [#32](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/32), [#61](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/61), [#69](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/69), and many more.
- Contributions to team based task: 
    - setting up gradle (PR [#7](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/7/files))
    - maintaining issue tracker (Issues [#21](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/21), [#22](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/22), [#23](https://github.com/AY2425S2-CS2113-F12-1/tp/issues/23))
    - Updating user/developer docs that are not specific to a feature: overall architecture class diagram in DG
    - Code refactoring to ensure SRP (PR [#57](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/57), [#74](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/74), [#76](https://github.com/AY2425S2-CS2113-F12-1/tp/pull/76))
