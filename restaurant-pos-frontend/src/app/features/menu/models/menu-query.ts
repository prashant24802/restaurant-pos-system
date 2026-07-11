export interface MenuQuery {

  page: number;

  size: number;

  search: string;

  sort: keyof MenuSortFields;

  direction: 'asc' | 'desc';

}

export interface MenuSortFields {

  id: number;

  name: string;

  categoryName: string;

  price: number;

}