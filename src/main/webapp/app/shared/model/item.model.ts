import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IItem {
  id?: number;
  name?: string;
  description?: string | null;
  category?: string | null;
  status?: string | null;
  creationDate?: dayjs.Dayjs;
  owner?: IUtilisateur | null;
}

export const defaultValue: Readonly<IItem> = {};
