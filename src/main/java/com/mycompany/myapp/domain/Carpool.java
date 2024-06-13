package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Carpool.
 */
@Entity
@Table(name = "carpool")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Carpool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "origin", nullable = false)
    private String origin;

    @NotNull
    @Column(name = "destination", nullable = false)
    private String destination;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @NotNull
    @Min(value = 1)
    @Column(name = "seats_available", nullable = false)
    private Integer seatsAvailable;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_carpool__passengers",
        joinColumns = @JoinColumn(name = "carpool_id"),
        inverseJoinColumns = @JoinColumn(name = "passengers_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur", "carpools" }, allowSetters = true)
    private Set<Passenger> passengers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "alerts", "carpools", "passengers", "properties" }, allowSetters = true)
    private Utilisateur driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Carpool id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return this.origin;
    }

    public Carpool origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return this.destination;
    }

    public Carpool destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Instant getDepartureTime() {
        return this.departureTime;
    }

    public Carpool departureTime(Instant departureTime) {
        this.setDepartureTime(departureTime);
        return this;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public Carpool seatsAvailable(Integer seatsAvailable) {
        this.setSeatsAvailable(seatsAvailable);
        return this;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getDescription() {
        return this.description;
    }

    public Carpool description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public Carpool price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Passenger> getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Carpool passengers(Set<Passenger> passengers) {
        this.setPassengers(passengers);
        return this;
    }

    public Carpool addPassengers(Passenger passenger) {
        this.passengers.add(passenger);
        return this;
    }

    public Carpool removePassengers(Passenger passenger) {
        this.passengers.remove(passenger);
        return this;
    }

    public Utilisateur getDriver() {
        return this.driver;
    }

    public void setDriver(Utilisateur utilisateur) {
        this.driver = utilisateur;
    }

    public Carpool driver(Utilisateur utilisateur) {
        this.setDriver(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carpool)) {
            return false;
        }
        return getId() != null && getId().equals(((Carpool) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carpool{" +
            "id=" + getId() +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            ", departureTime='" + getDepartureTime() + "'" +
            ", seatsAvailable=" + getSeatsAvailable() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
