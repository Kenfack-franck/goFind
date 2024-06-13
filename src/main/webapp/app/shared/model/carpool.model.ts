import dayjs from 'dayjs';
import { IPassenger } from 'app/shared/model/passenger.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ICarpool {
  id?: number;
  origin?: string;
  destination?: string;
  departureTime?: dayjs.Dayjs;
  seatsAvailable?: number;
  description?: string | null;
  price?: number | null;
  passengers?: IPassenger[] | null;
  driver?: IUtilisateur | null;
}

export const defaultValue: Readonly<ICarpool> = {};
