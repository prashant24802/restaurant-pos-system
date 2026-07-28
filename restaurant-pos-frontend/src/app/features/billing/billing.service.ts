import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Bill, PaymentMethod } from './billing.model';


@Injectable({
  providedIn: 'root'
})
export class BillingService {

  private http = inject(HttpClient);

  private api = 'http://localhost:8080/api/bills';

  getBills(): Observable<Bill[]> {
    return this.http.get<Bill[]>(this.api);
  }

  getBill(id: number): Observable<Bill> {
    return this.http.get<Bill>(`${this.api}/${id}`);
  }

  getBillByOrder(orderId: number): Observable<Bill> {
    return this.http.get<Bill>(
      `${this.api}/order/${orderId}`
    );
  }

  generateBill(orderId: number): Observable<Bill> {
    return this.http.post<Bill>(
      `${this.api}/order/${orderId}`,
      {}
    );
  }

 makePayment(
  billId: number,
  paymentMethod: PaymentMethod
) {
  return this.http.patch<Bill>(
    `${this.api}/${billId}/pay`,
    {
      paymentMethod
    }
  );
}

  getTodayBills(): Observable<Bill[]> {
    return this.http.get<Bill[]>(
      `${this.api}/today`
    );
  }

  getBillsByStatus(status: string): Observable<Bill[]> {
    return this.http.get<Bill[]>(
      `${this.api}/status/${status}`
    );
  }

}