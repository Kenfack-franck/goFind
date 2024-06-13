package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Carpool} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarpoolDTO implements Serializable {

    private Long id;

    @NotNull
    private String origin;

    @NotNull
    private String destination;

    @NotNull
    private Instant departureTime;

    @NotNull
    @Min(value = 1)
    private Integer seatsAvailable;

    private String description;

    private Double price;

    private Set<PassengerDTO> passengers = new HashSet<>();

    private UtilisateurDTO driver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public UtilisateurDTO getDriver() {
        return driver;
    }

    public void setDriver(UtilisateurDTO driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarpoolDTO)) {
            return false;
        }

        CarpoolDTO carpoolDTO = (CarpoolDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carpoolDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarpoolDTO{" +
            "id=" + getId() +
            ", origin='" + getOrigin() + "'" +
            ", destination='" + getDestination() + "'" +
            ", departureTime='" + getDepartureTime() + "'" +
            ", seatsAvailable=" + getSeatsAvailable() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", passengers=" + getPassengers() +
            ", driver=" + getDriver() +
            "}";
    }
}
