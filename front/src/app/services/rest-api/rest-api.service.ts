import { HttpClient } from '@angular/common/http';
import { Observable, of } from "rxjs";


export abstract class RestAPIService {

  protected _URL = 'http://localhost:8080/rurybooru/rest/';

  constructor( public http: HttpClient ) {
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  protected handleError<T>( operation = 'operation', result?: T ) {
    return ( error: any ): Observable<T> => {

      console.error(`${operation} failed: ${error.message}`, error); // log to console

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  get URL(): string {
    return this._URL;
  }
}
