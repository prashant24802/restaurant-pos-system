import { TableStatus } from './table';

export interface TableQuery {

  page: number;

  size: number;

  search: string;

  status: TableStatus | null;

  sort: string;

  direction: 'asc' | 'desc';

}