import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface PageResponse<T> {

  content: T[];

  totalElements: number;

  totalPages: number;

  size: number;

  number: number;

  first: boolean;

  last: boolean;

}
import { Order } from '../models/order.model';
import { CreateOrderRequest } from '../models/create-order-request.model';
import { AddOrderItemRequest } from '../models/add-order-item-request.model';


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly apiUrl = '${environment.apiUrl}/api/orders';

  constructor(
    private http: HttpClient
  ) { }

  getOrders(
    page: number,
    size: number,
    search?: string,
    status?: string,
    sort: string = 'createdAt,desc'
  ): Observable<PageResponse<Order>> {

    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    if (search) {
      params = params.set('search', search);
    }

    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<PageResponse<Order>>(
      this.apiUrl,
      { params }
    );
  }

  getOrder(
    id: number
  ): Observable<Order> {

    return this.http.get<Order>(
      `${this.apiUrl}/${id}`
    );

  }

  createOrder(
    request: CreateOrderRequest
  ): Observable<Order> {

    return this.http.post<Order>(
      this.apiUrl,
      request
    );

  }

  addItem(
    orderId: number,
    request: AddOrderItemRequest
  ): Observable<Order> {

    return this.http.post<Order>(
      `${this.apiUrl}/${orderId}/items`,
      request
    );

  }

  removeItem(
    orderId: number,
    orderItemId: number
  ): Observable<Order> {

    return this.http.delete<Order>(
      `${this.apiUrl}/${orderId}/items/${orderItemId}`
    );

  }

  updateStatus(
    orderId: number,
    status: string
  ): Observable<Order> {

    return this.http.patch<Order>(
      `${this.apiUrl}/${orderId}/status?status=${status}`,
      {}
    );

  }

  cancelOrder(
    orderId: number
  ): Observable<Order> {

    return this.http.patch<Order>(
      `${this.apiUrl}/${orderId}/cancel`,
      {}
    );

  }

}