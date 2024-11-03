/**
 * A helper class for representing a row in the Store table.
 * The class allows for the transformation of ResultSet to
 * an instance of a Java class with getters
 *
 * @author Brian T. Nixon
 */

package cs1530.dbdemo;

public class Store implements RowInterface {
    private Integer storeNumber;
    private String storeName;
    private String storeType;
    private String street;
    private String city;
    private String state;

    public Store(Integer storeNumber, String name, String storeType, String street, String city, String state) {
        this.storeNumber = storeNumber;
        this.storeName = name;
        this.storeType = storeType;
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public Integer getStoreNumber() {
        return storeNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "{" +
                "storeNumber=" + storeNumber +
                ", storeName='" + storeName + '\'' +
                ", storeType='" + storeType + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
