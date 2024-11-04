# CS 1530 Database Introduction Exercise

This exercise acts as a brief Demo / Tutorial for a guest lecture in CS 1530, Software Engineering, at the University of
Pittsburgh.

## Introduction

In this recitation exercise, you will practice connecting to an H2 database from a java application using JDBC. JDBC
allows the application to send dynamic SQL (Structured Query Language) statements to the database. These statements
include the creation of tables, modification of tables, and querying the tables for analysis. Your tasks will be to
store data for the *DataBrew* coffee chain by completing the necessary TODOs to learn how to connect to a relational
database from Java and how to perform simple database queries. Recall that *DataBrew* consists of Stores, Coffees, and
Receipts. You will implement the `findAll`, `findByStoreNumber`, `findByStoreType`, `findByCityAndState` methods of
`StoreQueryHelper`.

## Exercise

1) After the lecture on data storage and databases is finished, navigate to the exercise's GitHub repository (the one
   you're currently reading from)
   and fork the repository to create a separate copy for working off of. You can fork a repository in the top-right of
   this page.

   Review the structure of the repository.

   Navigate to the subdirectory `app/src/main/java/cs1530.dbdemo/` and note the following provided Java files:

    - `App.java` is a simple client application with a menu for performing database queries.
    - `Store.java` is a class for modeling a row in the Store table.
    - `Coffee.java` is a class for modeling a row in the Coffee table.
    - `Receipt.java` is a class for modeling a row in the Receipt table.
    - `RowInterface.java` acts as an interface implemented by `Coffee`, `Store`, and `Receipt` for easier printing in
      `App.java`
    - `StoreQueryHelper.java` is a class that performs H2 database queries on the Store table by sending SQL queries
      using JDBC. The class is utilized as an abstraction for the client app.
    - `CoffeeQueryHelper.java` is a class that performs H2 database queries on the Coffee table by sending SQL queries
      using JDBC. The class is utilized as an abstraction for the client app.
    - `ReceiptQueryHelper.java` is a class that performs H2 database queries on the Receipt table by sending SQL queries
      using JDBC. The class is utilized as an abstraction for the client app.

   In addition to the main portion of the code, test classes could be added in the subdirectory
   `app/src/test/java/cs1530.dbdemo/`.

Lastly, it's important to note the file `/app/build.gradle` which essentially tells Gradle what to run and any
dependencies (libraries) that need loaded into the project when building/running. While the first three dependencies
are fairly standard in a Gradle application, the last dependency `implementation 'com.h2database:h2:2.3.232'` is
responsible for loading the `.jar` for using the H2 database. If you swap to another DBMS, be sure to read the
installation instructions and acquire the correct `.jar` file if using JDBC.

2) To test compiling and building the setup, run `./gradlew build`. To run the application use `./gradlew run`. Although
   the menu application will run, several methods have been stubbed, will produce no output, and need completed.

3) Read the contents of `./app/src/main/java/cs1530.dbdemo/App.java`.
    1) First note the test properties being set and the JDBC `Connection` object created within the try-with-resources.
       Recall that JDBC is an API that offers several useful methods and features for interacting with a DBMS. An
       important note is the URL when connecting to the DBMS, which can be swapped out depending on the database. The
       URL always begins with `jdbc` followed by the DBMS being used, in our case `h2`, and other specifications for
       connecting to the DBMS (e.g., host, port, etc.). In our case, H2 is deployed locally, and we want to persist data
       to a file under the path `/app/database/h2_database_storage`. The second argument to the connection is always a
       Properties object that specifies additional connection requirements such as a username and password. In this
       case, the username and password for an H2 database is not important, but it's provided to show an example of how
       these properties can be given to JDBC.

    2) With the connection setup, the next few lines initialize some helpful classes for querying our H2 database,
       storing results from querying the database, and a simple menu for user interaction.
    3) Moving forward, we see several `TODO` comments within our switch case for the `findAll`, `findByStoreNumber`,
       `findByStoreType`, `findByCityAndState` methods of
       Store. Your first task is to complete these steps to retrieve input from the user (if needed) and call the
       appropriate method from the corresponding QueryHelper class. A few helpful hints: (1) A list of each task can be
       found at the bottom of `App.java` in the `displayMenu()` method, (2) the datatype for each input can be found in
       `Store.java`, (3) `Scanner.nextInt()` will not consume the entire input buffer, so it is often common to call
       `Scanner.nextLine()` directly after to *flush* the input buffer.
4) With this step completed, let's try running `./gradlew run`... Even though, we added the calls to
   `StoreQueryHelper.java` we're seeing errors being caught and handled from behind the scenes. Let's take a closer look
   at `StoreQueryHelper.java` to see what's missing.
    1) Note the constructor of `StoreQueryHelper`. The constructor takes in a JDBC `Connection` object, which is
       used to create a `Statement` object. You can think of the `Statement` class as providing a method to execute
       queries and updates to the database **that do not require any parameters.** Next, the statement executes several
       updates to the H2 database: `DROP TABLE <Table-Name> CASCADE` will delete the specified table from the H2
       database
       as well as any objects that depend on the table. We're using it in this example to ensure the table is clear, but
       be careful using this statement in practice. The second statement executed is
       `CREATE TABLE <Table-Name> (attribute-list)` which creates the specified table with the given attribute list.
       Note that `varchar(50)` is a varying length string with a maximum length of 50 characters. The second note is
       that the `Primary Key` attribute can be thought of as the primary identifier for rows in the table. The primary
       key cannot repeat values within the column and must always have a value (i.e., cannot be Null). After the table
       is created, sample data is inserted to the table using the `INSERT INTO <Table-name> VALUE (Value-list)` where
       the order of values matches the ordering of the table's columns.

5) At the first  `TODO` comment, complete the `findAll` method by using the `.executeQuery("SQL Query")` of the
   Statement class. In this comment, we will need to write our first SQL Query to retrieve all stores. The simplest form
   of an SQL Query is `SELECT * FROM <Table-name>;`. The `SELECT` keyword specifies which columns should be displayed in
   the result (`*` is a macro for all columns) and the `FROM` keyword specifies which table to query rows from. Given
   this format, complete the method by calling the `executeQuery("SQL Query")` to retrieve all Stores.

5) Let's test this function by running `./gradlew run` and selecting the `Find All Stores` option. If we were successful
   with the last step, all ten stores should be displayed on the console.

6) For the `TODO` comments in the `findByStoreNumber` and `findByStoreType` methods of `StoreQueryHelper.java`, we need
   to modify our query to be able to **filter rows** and for the query to be able to **accept user arguments** as part
   of the filtering.
    1) The first step is to expand our SQL Query to be able to filter for rows that satisfy a condition.
       This can be achieved by using the `WHERE` clause as follows: `SELECT * FROM <Table-Name> WHERE <condition>;`. The
       condition could be `name = 'Test Store'` (to filter for rows where the name is exactly Test Store), an
       inequality, or
       not equal to `<>`.
    2) The first modification allows us to filter rows in the query, we still need to set the condition to the parameter
       passed in to the method. When working with a SQL query that is dynamically generated using parameters, JDBC
       provides
       the PreparedStatement class that is initialized using `Connection.prepareStatement("SQL Query")`. Unlike the
       first
       `TODO`, a prepared statement will allow us to insert placeholders in the SQL Query using `?`. Next, we will need
       to set the argument of `?` by using the PreparedStatement's setter methods. There are many that can be seen on
       JDBC's official API, but we will mostly use `.setInt(i, value)` and `.setString(i, value)` to set the *ith*
       placeholder to the value.
    3) Now that we've written a dynamic query to filter rows, the last step is to run the query using
       `PreparedStatement.executeQuery()` and returning the results.
    4) A final note on this section. When dynamically generating a SQL Query using user parameters, **ALWAYS USE THE
       PREPARED STATEMENT AND NEVER USE STRING CONCATENATION** This is to avoid the SQL Injection security
       vulnerability.

7) The final `TODO` comments are to complete the `findByCityAndState` method of `StoreQueryHelper.java`. For this query,
   you will need to filter rows on two conditions, the equality of city and the equality of state. You can build on
   the previous SQL Query by utilizing `AND` to combine conditions in the `WHERE` clause. Similarly, the condition
   supports the use of `OR` and `NOT`. Given this updated query form, complete the `findByCityAndState` by utilizing the
   PreparedStatement and returning the result.

8) The last step is to verify that your code is working correctly using `./gradlew run` and to submit your code for the
   exercise on the Gradescope for the course.

## Conclusion

In this exercise, you wrote implementation code for connecting a Java app to an H2 database using JDBC. In addition, you
learned the basic form of an SQL Query, the structured query language, for retrieving data within a relational database.
There are many additional topics and further queries that were not discussed in this exercise such as joining multiple
tables together, computing statistical functions on columns, and nesting queries. These topics are discussed in much
greater detail in CS 1555: Database Management Systems.
