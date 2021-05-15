export interface Cart {
  id?: number;
  description: string;
  pizzaId?: number;
  drinkId?: number;
  snackId?: number;
  dessertId?: number;
  price: number;
  amount: number;
  userId?: number;
  size?: string;
  volume?: number;
}
