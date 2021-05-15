import {SizePizza} from './SizePizza';

export interface Size {
  id?: number;
  pizza_id: number;
  weight: number;
  diameter: number;
  size: SizePizza.SMALL | SizePizza.MEDIUM | SizePizza.LARGE;
  price: number;
  image: string;
  path: string;
  name: string;
}
