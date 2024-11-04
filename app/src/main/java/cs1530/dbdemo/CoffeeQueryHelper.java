/**
 * A helper class for querying the Coffee example table.
 * The class abstracts SQL query strings passed to JDBC
 * and provides common simple queries that might be used.
 *
 * @author Brian T. Nixon
 */

package cs1530.dbdemo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeQueryHelper {
    private static final int QUERY_TIMEOUT = 30;
    private Connection databaseConnection;

    /**
     * In the table below, note that numeric(5, 2) is a floating point
     * value with 5 significant digits, of which, 2 appear after the decimal point
     */
    public CoffeeQueryHelper(Connection conn) {
        this.databaseConnection = conn;
        try (Statement st = this.databaseConnection.createStatement()) {
            st.setQueryTimeout(QUERY_TIMEOUT); // set timeout to 30 seconds
            st.executeUpdate("DROP TABLE IF EXISTS COFFEE CASCADE;");
            st.executeUpdate("CREATE TABLE COFFEE (coffeeID integer PRIMARY KEY, " +
                    "name varchar(50)," +
                    "intensity integer," +
                    "price numeric(5,2));");
            // Can execute each update as a single statement
            st.executeUpdate("INSERT INTO COFFEE VALUES (1, 'Espresso', 10, 1.45);");
            st.executeUpdate("INSERT INTO COFFEE VALUES (2, 'Latte', 4, 3.65);");

            // Or execute multiple updates within a single statement
            st.executeUpdate("INSERT INTO COFFEE VALUES (3, 'Drip Coffee', 8, 1.15)," +
                    "(4, 'Cappuccino', 5, 2.79)," +
                    "(5, 'Macchiato', 5, 3.30)," +
                    "(6, 'Cold Brew', 7, 3.10)," +
                    "(7, 'Hot Chocolate', 1, 1.59)," +
                    "(8, 'Americano', 7, 2.05)," +
                    "(9, 'Mocha', 3, 2.65)," +
                    "(10, 'Frappuccino', 2, 2.89)");
        } catch (SQLException e) {
            handleError(e);
        }
    }

    /**
     * A helper function for querying all rows within a single table
     *
     * @return A List of all Sales in the H2 database
     */
    protected List<RowInterface> findAll() {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (Statement st = databaseConnection.createStatement()) {
            st.setQueryTimeout(QUERY_TIMEOUT); // set timeout to 30 seconds
            ResultSet rs = st.executeQuery("SELECT * FROM COFFEE;");
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A helper function for querying all Coffees with a
     * specified name
     *
     * @param name The name that queried Coffees will have
     * @return A list of all Coffees in the H2 database with the specified name
     */
    protected List<RowInterface> findByName(String name) {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM COFFEE WHERE name = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A helper function for querying all Coffees with a
     * specified coffee intensity
     *
     * @param intensity The intensity that queried Coffees will have
     * @return A list of all Coffees in the H2 database with the specified intensity
     */
    protected List<RowInterface> findByIntensity(int intensity) {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM COFFEE WHERE intensity = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setInt(1, intensity);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A helper function for querying all Coffees with a price
     * within the range lowerBound <= price <= upperBound
     *
     * @param lowerBound The lower bound on price
     * @param upperBound The upper bound on price
     * @return A list of all Coffees in the H2 database within the price range
     */
    protected List<RowInterface> findCoffeeInPriceRange(BigDecimal lowerBound, BigDecimal upperBound) {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * " +
                "FROM COFFEE " +
                "WHERE price >= ? AND price <= ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setBigDecimal(1, lowerBound);
            st.setBigDecimal(2, upperBound);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A private helper function for converting a row from ResultSet (JDBC's
     * standard return type for a query) to an instance of the Sale class.
     *
     * @param rs The ResultSet being iterated from executing a query
     * @return The Sale instance that matches the ResultSet's current row
     * @throws SQLException when the ResultSet is closed or another exception occurs
     *                      such as trying to access a column that is not part of the ResultSet
     */
    private static Coffee buildCoffeeFromRow(ResultSet rs) throws SQLException {
        return new Coffee(rs.getInt("coffeeID"),
                rs.getString("name"),
                rs.getInt("intensity"),
                rs.getDouble("price"));
    }

    /**
     * A helper function for handling errors that prints the error message,
     * SQL State, and the SQL Code for the error
     * @param err The SQLException being handled
     */
    private static void handleError(SQLException err) {
        System.err.println("The following error occurred while executing the query/update:");
        System.err.println("Message = " + err.getMessage());
        System.err.println("SQLState = " + err.getSQLState());
        System.err.println("SQL Code = " + err.getErrorCode());
    }
}
