export type TableStatus =
  | 'AVAILABLE'
  | 'OCCUPIED'
  | 'RESERVED'
  | 'OUT_OF_SERVICE';

export interface RestaurantTable {

  id: number;

  tableNumber: number;

  capacity: number;

  status: TableStatus;

}

export interface TableRequest {

  tableNumber: number;

  capacity: number;

}