package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Property;
import com.mycompany.myapp.domain.Rental;
import com.mycompany.myapp.domain.Utilisateur;
import com.mycompany.myapp.service.dto.PropertyDTO;
import com.mycompany.myapp.service.dto.RentalDTO;
import com.mycompany.myapp.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rental} and its DTO {@link RentalDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentalMapper extends EntityMapper<RentalDTO, Rental> {
    @Mapping(target = "renter", source = "renter", qualifiedByName = "utilisateurId")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    RentalDTO toDto(Rental s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
