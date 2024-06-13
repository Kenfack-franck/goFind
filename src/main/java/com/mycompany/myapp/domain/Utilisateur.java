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
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alerts", "owner" }, allowSetters = true)
    private Set<Item> items = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "item", "utilisateur" }, allowSetters = true)
    private Set<Alert> alerts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "passengers", "driver" }, allowSetters = true)
    private Set<Carpool> carpools = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur", "carpools" }, allowSetters = true)
    private Set<Passenger> passengers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rentals", "owner" }, allowSetters = true)
    private Set<Property> properties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Utilisateur name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Utilisateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Utilisateur password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Utilisateur phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setOwner(null));
        }
        if (items != null) {
            items.forEach(i -> i.setOwner(this));
        }
        this.items = items;
    }

    public Utilisateur items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public Utilisateur addItems(Item item) {
        this.items.add(item);
        item.setOwner(this);
        return this;
    }

    public Utilisateur removeItems(Item item) {
        this.items.remove(item);
        item.setOwner(null);
        return this;
    }

    public Set<Alert> getAlerts() {
        return this.alerts;
    }

    public void setAlerts(Set<Alert> alerts) {
        if (this.alerts != null) {
            this.alerts.forEach(i -> i.setUtilisateur(null));
        }
        if (alerts != null) {
            alerts.forEach(i -> i.setUtilisateur(this));
        }
        this.alerts = alerts;
    }

    public Utilisateur alerts(Set<Alert> alerts) {
        this.setAlerts(alerts);
        return this;
    }

    public Utilisateur addAlerts(Alert alert) {
        this.alerts.add(alert);
        alert.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeAlerts(Alert alert) {
        this.alerts.remove(alert);
        alert.setUtilisateur(null);
        return this;
    }

    public Set<Carpool> getCarpools() {
        return this.carpools;
    }

    public void setCarpools(Set<Carpool> carpools) {
        if (this.carpools != null) {
            this.carpools.forEach(i -> i.setDriver(null));
        }
        if (carpools != null) {
            carpools.forEach(i -> i.setDriver(this));
        }
        this.carpools = carpools;
    }

    public Utilisateur carpools(Set<Carpool> carpools) {
        this.setCarpools(carpools);
        return this;
    }

    public Utilisateur addCarpools(Carpool carpool) {
        this.carpools.add(carpool);
        carpool.setDriver(this);
        return this;
    }

    public Utilisateur removeCarpools(Carpool carpool) {
        this.carpools.remove(carpool);
        carpool.setDriver(null);
        return this;
    }

    public Set<Passenger> getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        if (this.passengers != null) {
            this.passengers.forEach(i -> i.setUtilisateur(null));
        }
        if (passengers != null) {
            passengers.forEach(i -> i.setUtilisateur(this));
        }
        this.passengers = passengers;
    }

    public Utilisateur passengers(Set<Passenger> passengers) {
        this.setPassengers(passengers);
        return this;
    }

    public Utilisateur addPassengers(Passenger passenger) {
        this.passengers.add(passenger);
        passenger.setUtilisateur(this);
        return this;
    }

    public Utilisateur removePassengers(Passenger passenger) {
        this.passengers.remove(passenger);
        passenger.setUtilisateur(null);
        return this;
    }

    public Set<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(Set<Property> properties) {
        if (this.properties != null) {
            this.properties.forEach(i -> i.setOwner(null));
        }
        if (properties != null) {
            properties.forEach(i -> i.setOwner(this));
        }
        this.properties = properties;
    }

    public Utilisateur properties(Set<Property> properties) {
        this.setProperties(properties);
        return this;
    }

    public Utilisateur addProperties(Property property) {
        this.properties.add(property);
        property.setOwner(this);
        return this;
    }

    public Utilisateur removeProperties(Property property) {
        this.properties.remove(property);
        property.setOwner(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return getId() != null && getId().equals(((Utilisateur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
