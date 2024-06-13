package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Alert;
import com.mycompany.myapp.domain.Item;
import com.mycompany.myapp.domain.Utilisateur;
import com.mycompany.myapp.service.dto.AlertDTO;
import com.mycompany.myapp.service.dto.ItemDTO;
import com.mycompany.myapp.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alert} and its DTO {@link AlertDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlertMapper extends EntityMapper<AlertDTO, Alert> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemId")
    @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName = "utilisateurId")
    AlertDTO toDto(Alert s);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemDTO toDtoItemId(Item item);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
