import { inject, Injectable } from '@angular/core';
import {
  HttpClient,
  HttpParams
} from '@angular/common/http';

import { Observable } from 'rxjs';

import {
  RestaurantTable,
  TableRequest
} from '../models/table';

import { TableQuery } from '../models/table-query';

export interface PageResponse<T> {

  content: T[];

  totalElements: number;

  totalPages: number;

  size: number;

  number: number;

  first: boolean;

  last: boolean;

}

@Injectable({
  providedIn: 'root'
})
export class TableService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/tables';

  getPage(
    query: TableQuery
  ): Observable<PageResponse<RestaurantTable>> {

    let params = new HttpParams()
      .set('page', query.page)
      .set('size', query.size)
      .set(
        'sort',
        `${query.sort},${query.direction}`
      );

    if (query.search.trim()) {

      params = params.set(
        'search',
        query.search.trim()
      );

    }

    if (query.status !== null) {

      params = params.set(
        'status',
        query.status
      );

    }

    return this.http.get<PageResponse<RestaurantTable>>(
      this.apiUrl,
      { params }
    );

  }

  getById(
    id: number
  ): Observable<RestaurantTable> {

    return this.http.get<RestaurantTable>(
      `${this.apiUrl}/${id}`
    );

  }

  create(
    request: TableRequest
  ): Observable<RestaurantTable> {

    return this.http.post<RestaurantTable>(
      this.apiUrl,
      request
    );

  }

  update(
    id: number,
    request: TableRequest
  ): Observable<RestaurantTable> {

    return this.http.put<RestaurantTable>(
      `${this.apiUrl}/${id}`,
      request
    );

  }

  delete(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

}