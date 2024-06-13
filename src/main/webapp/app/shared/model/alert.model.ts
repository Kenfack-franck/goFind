import dayjs from 'dayjs';
import { IItem } from 'app/shared/model/item.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IAlert {
  id?: number;
  message?: string;
  date?: dayjs.Dayjs;
  read?: boolean | null;
  item?: IItem | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<IAlert> = {
  read: false,
};
