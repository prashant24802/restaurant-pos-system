import { inject, Injectable } from '@angular/core';
import {
  HttpClient,
  HttpParams
} from '@angular/common/http';

import { Observable } from 'rxjs';

import { MenuItem } from '../models/menu-item';
import { MenuItemRequest } from '../models/menu-item-request';
import { PageResponse } from '../models/page-response';
import { MenuQuery } from '../models/menu-query';

@Injectable({
  providedIn: 'root'
})
export class Menu {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/menu/items';

  getAll(): Observable<MenuItem[]> {

    return this.http.get<MenuItem[]>(this.apiUrl);

  }

  getPage(query: MenuQuery): Observable<PageResponse<MenuItem>> {

    const params = new HttpParams()
      .set('page', query.page)
      .set('size', query.size)
      .set('sort', `${query.sort},${query.direction}`);

    return this.http.get<PageResponse<MenuItem>>(
      `${this.apiUrl}/page`,
      { params }
    );

  }

  getById(id: number): Observable<MenuItem> {

    return this.http.get<MenuItem>(
      `${this.apiUrl}/${id}`
    );

  }

  create(request: MenuItemRequest): Observable<MenuItem> {

    return this.http.post<MenuItem>(
      this.apiUrl,
      request
    );

  }

  update(
    id: number,
    request: MenuItemRequest
  ): Observable<MenuItem> {

    return this.http.put<MenuItem>(
      `${this.apiUrl}/${id}`,
      request
    );

  }

  delete(id: number): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

  search(keyword: string): Observable<MenuItem[]> {

    const params = new HttpParams()
      .set('keyword', keyword);

    return this.http.get<MenuItem[]>(
      `${this.apiUrl}/search`,
      { params }
    );

  }

}