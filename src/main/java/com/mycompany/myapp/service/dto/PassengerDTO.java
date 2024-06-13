package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Passenger} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PassengerDTO implements Serializable {

    private Long id;

    private String status;

    @NotNull
    private Instant joinDate;

    private UtilisateurDTO utilisateur;

    private Set<CarpoolDTO> carpools = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<CarpoolDTO> getCarpools() {
        return carpools;
    }

    public void setCarpools(Set<CarpoolDTO> carpools) {
        this.carpools = carpools;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassengerDTO)) {
            return false;
        }

        PassengerDTO passengerDTO = (PassengerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, passengerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassengerDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            ", utilisateur=" + getUtilisateur() +
            ", carpools=" + getCarpools() +
            "}";
    }
}
