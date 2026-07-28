import { OrderItem } from './order-item.model';

export interface Order {

  id: number;

  tableId: number;

  tableNumber: number;

  status: string;

  subtotal: number;

  tax: number;

  totalAmount: number;

  createdAt: string;

  items: OrderItem[];

}