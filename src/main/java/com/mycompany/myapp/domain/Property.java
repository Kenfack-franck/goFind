package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description")
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "property_size")
    private Integer propertySize;

    @Column(name = "type")
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "renter", "property" }, allowSetters = true)
    private Set<Rental> rentals = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "alerts", "carpools", "passengers", "properties" }, allowSetters = true)
    private Utilisateur owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Property id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public Property location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public Property description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public Property price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAvailabilityStatus() {
        return this.availabilityStatus;
    }

    public Property availabilityStatus(String availabilityStatus) {
        this.setAvailabilityStatus(availabilityStatus);
        return this;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Integer getPropertySize() {
        return this.propertySize;
    }

    public Property propertySize(Integer propertySize) {
        this.setPropertySize(propertySize);
        return this;
    }

    public void setPropertySize(Integer propertySize) {
        this.propertySize = propertySize;
    }

    public String getType() {
        return this.type;
    }

    public Property type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Rental> getRentals() {
        return this.rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        if (this.rentals != null) {
            this.rentals.forEach(i -> i.setProperty(null));
        }
        if (rentals != null) {
            rentals.forEach(i -> i.setProperty(this));
        }
        this.rentals = rentals;
    }

    public Property rentals(Set<Rental> rentals) {
        this.setRentals(rentals);
        return this;
    }

    public Property addRentals(Rental rental) {
        this.rentals.add(rental);
        rental.setProperty(this);
        return this;
    }

    public Property removeRentals(Rental rental) {
        this.rentals.remove(rental);
        rental.setProperty(null);
        return this;
    }

    public Utilisateur getOwner() {
        return this.owner;
    }

    public void setOwner(Utilisateur utilisateur) {
        this.owner = utilisateur;
    }

    public Property owner(Utilisateur utilisateur) {
        this.setOwner(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return getId() != null && getId().equals(((Property) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", availabilityStatus='" + getAvailabilityStatus() + "'" +
            ", propertySize=" + getPropertySize() +
            ", type='" + getType() + "'" +
            "}";
    }
}
