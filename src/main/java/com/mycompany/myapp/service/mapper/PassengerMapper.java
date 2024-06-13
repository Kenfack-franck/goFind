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
 * Mapper for the entity {@link Passenger} and its DTO {@link PassengerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PassengerMapper extends EntityMapper<PassengerDTO, Passenger> {
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    @Mapping(target = "carpools", source = "carpools", qualifiedByName = "carpoolIdSet")
    PassengerDTO toDto(Passenger s);

    @Mapping(target = "carpools", ignore = true)
    @Mapping(target = "removeCarpools", ignore = true)
    Passenger toEntity(PassengerDTO passengerDTO);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);

    @Named("carpoolId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarpoolDTO toDtoCarpoolId(Carpool carpool);

    @Named("carpoolIdSet")
    default Set<CarpoolDTO> toDtoCarpoolIdSet(Set<Carpool> carpool) {
        return carpool.stream().map(this::toDtoCarpoolId).collect(Collectors.toSet());
    }
}
