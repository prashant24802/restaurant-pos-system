import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { MenuItem } from '../models/menu-item';
import { MenuItemRequest } from '../models/menu-item-request';

@Injectable({
  providedIn: 'root'
})
export class Menu {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/menu/items';

  getAll(): Observable<MenuItem[]> {

    return this.http.get<MenuItem[]>(this.apiUrl);

  }

  create(request: MenuItemRequest): Observable<MenuItem> {

    return this.http.post<MenuItem>(this.apiUrl, request);

  }

}