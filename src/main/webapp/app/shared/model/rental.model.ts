import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { IProperty } from 'app/shared/model/property.model';

export interface IRental {
  id?: number;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  status?: string | null;
  price?: number;
  renter?: IUtilisateur | null;
  property?: IProperty | null;
}

export const defaultValue: Readonly<IRental> = {};
