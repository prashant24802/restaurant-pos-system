import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/menu/categories';

  getAll(): Observable<Category[]> {

    return this.http.get<Category[]>(this.apiUrl);

  }

}