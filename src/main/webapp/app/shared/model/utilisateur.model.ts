export interface IUtilisateur {
  id?: number;
  name?: string;
  email?: string;
  password?: string;
  phoneNumber?: string | null;
}

export const defaultValue: Readonly<IUtilisateur> = {};
