# Budget Buddy

## About the application

### What this application is
Budget Buddy is a dynamic budget tracking app designed to make
managing your finances a breeze. With insights about your
spending habits, monthly and weekly statistics, and ability 
to set realistic budget limits, Budget Buddy helps you 
develop healthy financial habits and makes adulting a 
little bit easier. 

### Who this application is for
This project is tailored to meet the unique needs of students 
and young adults and empower them in their financial journey. 
With Budget Buddy, they can take their mind off of managing 
money and focus on their academic and personal goals.

### Why this application is of interest to me
As an international student who struggled with finances and
making sure I'm not spending too much money, I understand the
hardships of starting college and having to be independent. 
With Budget Buddy, I aim to make this process smoother for 
people in the same shoes as I was. 

## User Stories
- As a user, I want to be able to set a monthly budget.
- As a user, I want bills and essentials to automatically be added my spending tracker.
- As a user, I want to be able to add transactions to my spending tracker. 
- As a user, I want to be able to see my transaction history.
- As a user, I want to be able to see how much money I have left to spend in a month
- As a user, I want to be able to be able to load my monthly spending tracker from file (if I so choose)
- As a user, I want to be able to save my monthly spending tracker to file (if I so choose)

# Instructions for Grader
- You can add a transaction to the transaction history by going to the transactions page, filling out the 
transaction information on the right side of the screen, and then clicking on the "Add transaction" button. 
- You can calculate the total amount of money spent in a particular timeframe by going to the transactions page,
selecting which timeframe you want to see, or by giving your own custom timeframe, and then clicking on the
"Calculate your total" button.
- You can locate the visual component every time after you complete an action that changes the app state (like adding
a transaction, changing your budget, etc.).
- You can save the state of the application by clicking on exit on the bottom left corner and then selecting "Yes"
on the "Do you want to save" prompt. 
- You can reload the state of the application by selecting "Yes" on the "Do you want to load save data" prompt
when the app is launched.

# Phase 4: Task 2
~~~
Thu Aug 10 03:14:09 PDT 2023
Added transaction: 
Name:McDonald's
Cost: 20
Date: 07/08/2023
Category Eating out

Thu Aug 10 03:14:21 PDT 2023
Added recurring transaction: 
Name:Rent
Cost: 1000
Date: 01/08/2023
Category Bills

Thu Aug 10 03:14:55 PDT 2023
Added transaction: 
Name:Movies
Cost: 15
Date: 01/07/2023
Category Miscellaneous

Thu Aug 10 03:15:04 PDT 2023
Budget changed to 1000
~~~

# Phase 4: Task 3
In its current state, the app only allows for one user 
to save their data and is rather simple. If I had more time to work on the project, I would try to 
implement a list of users and maybe even a database 
which could open up the possibility of having multiple
users with different login credentials. I would also add 
functionality to remove transactions from the user's transaction
history and change details of the transaction like its 
cost, category, name, or date. 

Upon inspecting my code, particularly the User class, I found that
the list of essentials could also have been implemented
as a TransactionList. However, the list of essentials does not
require all the methods and functionality that the TransactionList
class provides. A more reasonable approach would be to make an abstract
class that provides some basic functionality and have two classes,
one for the TransactionList and one for the EssentialsList, extend it
and implement additional methods specialized to their needs. 


