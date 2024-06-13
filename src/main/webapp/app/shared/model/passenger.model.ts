import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ICarpool } from 'app/shared/model/carpool.model';

export interface IPassenger {
  id?: number;
  status?: string | null;
  joinDate?: dayjs.Dayjs;
  utilisateur?: IUtilisateur | null;
  carpools?: ICarpool[] | null;
}

export const defaultValue: Readonly<IPassenger> = {};
