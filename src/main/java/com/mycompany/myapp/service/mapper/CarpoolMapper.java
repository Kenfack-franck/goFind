package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Carpool;
import com.mycompany.myapp.domain.Passenger;
import com.mycompany.myapp.domain.Utilisateur;
import com.mycompany.myapp.service.dto.CarpoolDTO;
import com.mycompany.myapp.service.dto.PassengerDTO;
import com.mycompany.myapp.service.dto.UtilisateurDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Carpool} and its DTO {@link CarpoolDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarpoolMapper extends EntityMapper<CarpoolDTO, Carpool> {
    @Mapping(target = "passengers", source = "passengers", qualifiedByName = "passengerIdSet")
    @Mapping(target = "driver", source = "driver", qualifiedByName = "utilisateurId")
    CarpoolDTO toDto(Carpool s);

    @Mapping(target = "removePassengers", ignore = true)
    Carpool toEntity(CarpoolDTO carpoolDTO);

    @Named("passengerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PassengerDTO toDtoPassengerId(Passenger passenger);

    @Named("passengerIdSet")
    default Set<PassengerDTO> toDtoPassengerIdSet(Set<Passenger> passenger) {
        return passenger.stream().map(this::toDtoPassengerId).collect(Collectors.toSet());
    }

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
