import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { MenuItem } from '../models/menu-item';

@Injectable({
  providedIn: 'root'
})
export class Menu {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/menu/items';

  getAll(): Observable<MenuItem[]> {

    return this.http.get<MenuItem[]>(this.apiUrl);

  }

}