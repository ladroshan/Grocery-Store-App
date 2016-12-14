# Grocery-Store-App User Manual
Application for Intro to Software Engineering Project

# How To Use This App
1. Fork repo to your local computer.
2. Before running the executable, run the following lines of code in your local computer's PostgreSQL:
	a) CREATE DATABASE scangro;
	b) CREATE ROLE app SUPERUSER;
	c) ALTER ROLE app WITH PASSWORD 'Th3Cak3IsALi3!';
	d) ALTER ROLE app LOGIN;
	e) \i '~PATH~/Grocery-Store-App/lib/scangro_release.sql'  (As a side note, you must replace the ~PATH~ in the 
		quotations with the path to the folder you put the forked Grocery-Store-App files in)
3. Run the ScanableGrocery executable file that is in the Grocery-Store-App folder. 
	Current executable name: ScannableGrocery_v1_0.jar
4. When the app starts, you will be required to log into the app. Notice that there are some documentational
   resources available to even unauthenticated users through the MenuBar along the top of the app window. However, 
   to reach the meat of the program, you will need to be logged in. Initially, the login credentials will be as 
   follows:
							User: admin			Password: secure
5. Once you are logged in, there are 2 kinds of access that you may have: a) Admin access (AKA Full access) or 
   b) Cashier Access. You will notice that the Cashier doesn't have much more access to the program than the 
   unauthenticated user. On top of being able to run the document options from the menu, a Cashier can Logout,
   access the Checkout function, or look up Receipts.
6. The Checkout function is the central program functionality at this time. You can click on the Go to Checkout
   button or the Checkout option in the dropdown menu to visit the first page of the checkout. From here you 
   can enter the id number for an item that is in the inventory datbase and it will decrement the availability of 
   that item. If an item is decremented below 1, the item will be removed from the database listing.
7. As an Admin you will have access to adding editing and removing items from inventory you simply select to work 
   with your inventory. On that page you may choose to add or edit on selecting edit it will take you to a table. On
   the Table you will be able to select the Item name provider price and quantity of the of each inventory item.
   you may only make adjustments to one row at a time enter in the changes then select the edit row button in the 
   Update item colomn to submit those changes back to the database. any changes to the Id will not be saved to the
   database and by selecting delete row will delete the item selected from the database.
8. Another feature is the ability to add or edit the users when selecting edit you will see the table displaying the
   User Name, password, Id, is admin, on this page you may select and change the user name and password the same way.
