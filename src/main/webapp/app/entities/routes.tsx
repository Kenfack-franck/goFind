import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Utilisateur from './utilisateur';
import Item from './item';
import Alert from './alert';
import Carpool from './carpool';
import Passenger from './passenger';
import Property from './property';
import Rental from './rental';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="utilisateur/*" element={<Utilisateur />} />
        <Route path="item/*" element={<Item />} />
        <Route path="alert/*" element={<Alert />} />
        <Route path="carpool/*" element={<Carpool />} />
        <Route path="passenger/*" element={<Passenger />} />
        <Route path="property/*" element={<Property />} />
        <Route path="rental/*" element={<Rental />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
