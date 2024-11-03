/**
 * A helper class for representing a row in the Coffee table.
 * The class allows for the transformation of ResultSet to
 * an instance of a Java class with getters
 *
 * @author Brian T. Nixon
 */

package cs1530.dbdemo;

public class Coffee implements RowInterface {
    private Integer coffeeID;
    private String name;
    private Integer intensity;
    private Double price;

    public Coffee(Integer coffeeID, String name, Integer intensity, Double price) {
        this.coffeeID = coffeeID;
        this.name = name;
        this.intensity = intensity;
        this.price = price;
    }

    public Integer getCoffeeID() {
        return coffeeID;
    }

    public String getName() {
        return name;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{" +
                "coffeeID='" + coffeeID + '\'' +
                ", name='" + name + '\'' +
                ", intensity=" + intensity +
                ", price=" + price +
                '}';
    }
}
