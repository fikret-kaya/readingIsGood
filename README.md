ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one **centralized** warehouse to their
customers within the same day. That is why stock **consistency** is the first priority for their
vision operations.

**In this case, we will only consider;**

- Registering New Customer
- Placing a new order
- Tracking the stock of books
- List all orders of the customer
- Viewing the order details
- Query Monthly Statistics

**Must Have Requirements:**

- Customer Controller
	- Will persist new customers
	- Will query all orders of the customer ( Paging sounds really nice )
- Book Controller
	- Will persist new book
	- Will update book’s stock
- Order Controller
	- Will persist new order (statuses may used)
		- Will update stock records.
		  (Hint: what if it happens if 2 or more users tries to buy one last book
		  at the same time)
	- Will query order by Id
	- List orders by date interval ( startDate - endDate )


- Statistics Controller
	- Will serve customer’s monthly order statistics
		- Total Order count
		- Total amount of all purchased orders
		- Total count of purchased books
		  That endpoint will supply data for the following ui component
		

- **Authentication** - Please secure endpoints (for ex. bearer token)

**Nice to Have’s:**

- **Logging** - Log all changes on entities. (Which user made specific changes and when)
- **Open API Specification** ( Swagger sounds nice ) (authentication not needed)

