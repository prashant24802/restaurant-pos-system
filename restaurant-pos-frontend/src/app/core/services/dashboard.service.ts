import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Dashboard } from '../models/dashboard';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private http = inject(HttpClient);

  private readonly api =
    '${environment.apiUrl}/api/dashboard';

  getSummary(): Observable<Dashboard> {

    return this.http.get<Dashboard>(
      `${this.api}/summary`
    );

  }

}