import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IProperty {
  id?: number;
  location?: string;
  description?: string | null;
  price?: number;
  availabilityStatus?: string | null;
  propertySize?: number | null;
  type?: string | null;
  owner?: IUtilisateur | null;
}

export const defaultValue: Readonly<IProperty> = {};
