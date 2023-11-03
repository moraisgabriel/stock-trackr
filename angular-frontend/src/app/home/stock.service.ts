import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { Product } from './home.component';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  private url = 'http://localhost:8080/stock';

  constructor(private httpClient: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  getAllStockProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.url)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
  }

  addProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.url, JSON.stringify(product), this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      )
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Erro ocorreu no lado do client
      errorMessage = error.error.message;
    } else {
      // Erro ocorreu no lado do servidor
      errorMessage = `Código do erro: ${error.status}, ` + `menssagem: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => new Error(errorMessage));
  };
}
