export interface Bill {

  id: number;

  invoiceNumber: string;

  orderId: number;

  tableNumber: string;

  subtotal: number;

  tax: number;

  discount: number;

  totalAmount: number;

  paymentMethod: PaymentMethod | null;

  paymentStatus: PaymentStatus;

  billedAt: string;

  paidAt: string | null;

  items: BillItem[];

}

export interface BillItem {

  itemName: string;

  quantity: number;

  unitPrice: number;

  total: number;

}

export type PaymentMethod =
  | 'CASH'
  | 'CARD'
  | 'UPI';

export type PaymentStatus =
  | 'PENDING'
  | 'PAID'
  | 'FAILED';