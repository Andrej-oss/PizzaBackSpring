export interface Purchase {
  id?: number;
  name: string;
  description: string;
  price: number;
  userId: number;
  volume: string;
  pizzaId?: number;
  drinkId?: number;
  snackId?: number;
  dessertId?: number;
  date?: any;
  amount?: number;
}
