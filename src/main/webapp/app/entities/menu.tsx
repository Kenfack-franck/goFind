import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/utilisateur">
        <Translate contentKey="global.menu.entities.utilisateur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/item">
        <Translate contentKey="global.menu.entities.item" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/alert">
        <Translate contentKey="global.menu.entities.alert" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/carpool">
        <Translate contentKey="global.menu.entities.carpool" />
      </MenuItem>
      {/* <MenuItem icon="asterisk" to="/passenger">
        <Translate contentKey="global.menu.entities.passenger" />
      </MenuItem> */}
      <MenuItem icon="asterisk" to="/property">
        <Translate contentKey="global.menu.entities.property" />
      </MenuItem>
      {/* <MenuItem icon="asterisk" to="/rental">
        <Translate contentKey="global.menu.entities.rental" />
      </MenuItem> */}
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
