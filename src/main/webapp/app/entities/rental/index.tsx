import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rental from './rental';
import RentalDetail from './rental-detail';
import RentalUpdate from './rental-update';
import RentalDeleteDialog from './rental-delete-dialog';

const RentalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rental />} />
    <Route path="new" element={<RentalUpdate />} />
    <Route path=":id">
      <Route index element={<RentalDetail />} />
      <Route path="edit" element={<RentalUpdate />} />
      <Route path="delete" element={<RentalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RentalRoutes;
