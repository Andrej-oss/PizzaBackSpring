import {Purchase} from './Purchase';

export interface PurchasePage {
  purchases: Purchase[];
  totalElements: number;
  size: number;
  totalPages: number;
  number: number;
}
