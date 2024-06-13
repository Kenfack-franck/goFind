import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import item from 'app/entities/item/item.reducer';
import alert from 'app/entities/alert/alert.reducer';
import carpool from 'app/entities/carpool/carpool.reducer';
import passenger from 'app/entities/passenger/passenger.reducer';
import property from 'app/entities/property/property.reducer';
import rental from 'app/entities/rental/rental.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  utilisateur,
  item,
  alert,
  carpool,
  passenger,
  property,
  rental,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
