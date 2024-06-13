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
 * A Passenger.
 */
@Entity
@Table(name = "passenger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "join_date", nullable = false)
    private Instant joinDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "items", "alerts", "carpools", "passengers", "properties" }, allowSetters = true)
    private Utilisateur utilisateur;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "passengers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "passengers", "driver" }, allowSetters = true)
    private Set<Carpool> carpools = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Passenger id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public Passenger status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getJoinDate() {
        return this.joinDate;
    }

    public Passenger joinDate(Instant joinDate) {
        this.setJoinDate(joinDate);
        return this;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Passenger utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public Set<Carpool> getCarpools() {
        return this.carpools;
    }

    public void setCarpools(Set<Carpool> carpools) {
        if (this.carpools != null) {
            this.carpools.forEach(i -> i.removePassengers(this));
        }
        if (carpools != null) {
            carpools.forEach(i -> i.addPassengers(this));
        }
        this.carpools = carpools;
    }

    public Passenger carpools(Set<Carpool> carpools) {
        this.setCarpools(carpools);
        return this;
    }

    public Passenger addCarpools(Carpool carpool) {
        this.carpools.add(carpool);
        carpool.getPassengers().add(this);
        return this;
    }

    public Passenger removeCarpools(Carpool carpool) {
        this.carpools.remove(carpool);
        carpool.getPassengers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passenger)) {
            return false;
        }
        return getId() != null && getId().equals(((Passenger) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Passenger{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            "}";
    }
}
