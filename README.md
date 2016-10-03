# Grocery-Store-App
Application for Intro to Software Engineering Project

# How To Use This App
1. Fork repo to your local computer and open the project in Java.
2. Compile, Debug, or Run the app from there. The Class called "Mainframe.class" should be your starting point.
3. When the app starts, you will be required to log into the app. Notice that there are some documentational
   resources available to even unauthenticated users through the MenuBar along the top of the app window. However, 
   to reach the meat of the program, you will need to be logged in.
4. Once you are logged in, there are 2 kinds of access that you may have: a) Admin access (AKA Full access) or 
   b) Cashier Access. You will notice that the Cashier doesn't have much more access to the program than the 
   unauthenticated user. On top of being able to run the document options from the menu, a Cashier can Logout or
   access the Checkout function.
5. The Checkout function is the central program functionality at this time. You can click on the Go to Checkout
   button or the Checkout option in the dropdown menu to visit the first page of the checkout. From here you 
   can enter the id number for an item that is in the inventory datbase and it will decrement the availability of 
   that item. If an item is decremented below 1, the item will be removed from the database listing.
6. As an Admin you will have access to adding editing and removing items from inventory you simply select to work 
   with your inventory. on that page you may choose to add or edit on selecting edit it will take you to a table. On
   the Table you will be able to select the Item name provider price and quantity of the of each inventory item.
   you may only make adjustments to one row at a time enter in the changes then select the edit row button in the 
   Update item colomn to submit those changes back to the database. any changes to the Id will not be saved to the database.
   and by selecting delete row will delete the item selected from the database.
7. Another feature is the ability to add or edit the users when selecting edit you will see the table displaying the User Name,
   password, Id, is admin, on this page you may select and change the user name and password the same way.
