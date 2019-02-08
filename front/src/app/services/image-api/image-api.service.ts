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

  constructor( public http: HttpClient ) {
    super(http);
  }

  getImages( perPage: number, page: number, tags: number[], order: ImageOrder ): Observable<Image[]> {
    let query = '?';
    query += 'perPage=' + perPage + '&';
    query += 'page=' + page + '&';
    if (tags) {
      tags.forEach(t => query += 'tags=' + t + '&');
    }
    query += 'order=' + order;
    return this.http.get<Image[]>(this.getImageUrl() + query)
      .pipe(
        catchError(this.handleError('getImages', []))
      );
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
}
