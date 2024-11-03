/**
 * A helper class for representing a row in the Receipt table.
 * The class allows for the transformation of ResultSet to
 * an instance of a Java class with getters
 *
 * @author Brian T. Nixon
 */

package cs1530.dbdemo;

import java.sql.Timestamp;

public class Receipt implements RowInterface {
    private Integer receiptID;
    private Integer storeNumber;
    private Timestamp timeOfPurchase;
    private Integer coffeeID;
    private Integer quantity;

    public Receipt(Integer receiptId, Integer storeNumber, Timestamp timeOfPurchase, Integer coffeeID, Integer quantity) {
        this.receiptID = receiptId;
        this.storeNumber = storeNumber;
        this.timeOfPurchase = timeOfPurchase;
        this.coffeeID = coffeeID;
        this.quantity = quantity;
    }

    public Integer getReceiptId() {
        return receiptID;
    }

    public Integer getStoreNumber() {
        return storeNumber;
    }

    public Timestamp getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public Integer getCoffeeID() {
        return coffeeID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "{" +
                "receiptId=" + receiptID +
                ", storeNumber=" + storeNumber +
                ", timeOfPurchase=" + timeOfPurchase +
                ", coffeeID=" + coffeeID +
                ", quantity=" + quantity +
                '}';
    }
}
