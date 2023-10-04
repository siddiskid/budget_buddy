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


