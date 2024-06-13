package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Property;
import com.mycompany.myapp.domain.Utilisateur;
import com.mycompany.myapp.service.dto.PropertyDTO;
import com.mycompany.myapp.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Property} and its DTO {@link PropertyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "utilisateurId")
    PropertyDTO toDto(Property s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
