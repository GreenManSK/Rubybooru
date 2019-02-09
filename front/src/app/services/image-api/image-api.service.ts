import { Injectable } from '@angular/core';
import { RestAPIService } from "../rest-api/rest-api.service";
import { Observable } from "rxjs";
import { Image } from "../../entities/image";
import { catchError } from "rxjs/operators";
import { HttpClient, HttpHeaders, HttpParams } from "../../../../node_modules/@angular/common/http";
import { ImageOrder } from "../../views/search/image-order.enum";


@Injectable({
  providedIn: 'root'
})
export class ImageApiService extends RestAPIService {

  private IMAGE_GET = 'image';
  private IMAGE_TMP = 'image/tmp/';
  private IMAGE_FILE = 'image/file/';
  private IMAGE_OPEN = 'image/open/';
  private IMAGE_COUNT = 'image/count/';

  constructor( public http: HttpClient ) {
    super(http);
  }

  getImages( perPage: number, page: number, tags: number[], order: ImageOrder, filters: string[] = null ): Observable<Image[]> {
    const query = this._builtImageFilterQuery(tags, filters, perPage, page, order);
    return this.http.get<Image[]>(this.getImageUrl() + query)
      .pipe(
        catchError(this.handleError('getImages', []))
      );
  }

  getImageCount( tags: number[], filters: string[] = null ): Observable<number> {
    const query = this._builtImageFilterQuery(tags, filters);
    return this.http.get<number>(this.getImageCountUrl() + query)
      .pipe(
        catchError(this.handleError('getImageCount', 0))
      );
  }

  _builtImageFilterQuery( tags?: number[], filters?: string[], perPage?: number, page?: number, order?: ImageOrder ): string {
    let query = '?';

    if (perPage !== null) {
      query += 'perPage=' + perPage + '&';
    }

    if (page !== null) {
      query += 'page=' + page + '&';
    }

    if (tags) {
      tags.forEach(t => query += 'tags=' + t + '&');
    }

    if (filters) {
      filters.forEach(t => query += 'filters=' + t + '&');
    }

    if (order !== null) {
      query += 'order=' + order;
    }

    return query;
  }

  getImage( id: number ): Observable<Image> {
    return this.http.get<Image>(this.getImageUrl(id)).pipe(
      catchError(this.handleError<Image>('getImage(' + id + ')', null))
    );
  }

  openImage( id: number ): Observable<any> {
    return this.http.get(this.getOpenImageUrl(id))
      .pipe(
        catchError(this.handleError('openImage(' + id + ')', []))
      );
  }

  /** URLs */
  getImageThumbUrl( id: number, width: number, height: number ): string {
    return this._URL + this.IMAGE_TMP + id + '?width=' + width + '&height=' + height;
  }

  getOpenImageUrl( id: number ): string {
    return this._URL + this.IMAGE_OPEN + id;
  }

  getImageUrl( id: number = null ): string {
    return this._URL + this.IMAGE_GET + (id !== null ? '/' + id : '');
  }

  getImageFileUrl( id: number ): string {
    return this._URL + this.IMAGE_FILE + (id !== null ? '/' + id : '');
  }

  getImageCountUrl(): string {
    return this._URL + this.IMAGE_COUNT;
  }
}
