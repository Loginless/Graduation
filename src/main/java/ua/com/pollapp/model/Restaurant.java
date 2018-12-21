package ua.com.pollapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "restaurant_unique_restaurant_id_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "address", nullable = false, unique = false)
    @NotBlank
    @Size(max = 100)
    private String address;

    @Column(name = "phone_number", nullable = false, unique = false)
    @NotBlank
    @Size(max = 100)
    private String phoneNumber;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date DESC")
    protected List<Menu> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String restaurantAddress, String restaurantTelNumber) {
        this.name = restaurantName;
        this.address = restaurantAddress;
        this.phoneNumber = restaurantTelNumber;
    }

    public Restaurant(int restaurantID, String restaurantName, String restaurantAddress, String restaurantTelNumber) {
        this.id = restaurantID;
        this.name = restaurantName;
        this.address = restaurantAddress;
        this.phoneNumber = restaurantTelNumber;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
