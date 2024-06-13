import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Carpool from './carpool';
import CarpoolDetail from './carpool-detail';
import CarpoolUpdate from './carpool-update';
import CarpoolDeleteDialog from './carpool-delete-dialog';

const CarpoolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Carpool />} />
    <Route path="new" element={<CarpoolUpdate />} />
    <Route path=":id">
      <Route index element={<CarpoolDetail />} />
      <Route path="edit" element={<CarpoolUpdate />} />
      <Route path="delete" element={<CarpoolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CarpoolRoutes;
