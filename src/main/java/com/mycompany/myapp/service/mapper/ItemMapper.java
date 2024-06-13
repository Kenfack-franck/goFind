package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Item;
import com.mycompany.myapp.domain.Utilisateur;
import com.mycompany.myapp.service.dto.ItemDTO;
import com.mycompany.myapp.service.dto.UtilisateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "utilisateurId")
    ItemDTO toDto(Item s);

    @Named("utilisateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UtilisateurDTO toDtoUtilisateurId(Utilisateur utilisateur);
}
