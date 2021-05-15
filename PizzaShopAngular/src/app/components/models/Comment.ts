import {Voice} from './Voice';

export interface Comment {
  id?: number;
  pizzaId?: number;
  userId?: number;
  author?: string;
  tittle: string;
  body: string;
  voice?: Voice[];
  date?: number;
}
