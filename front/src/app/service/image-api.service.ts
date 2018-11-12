import { Injectable } from '@angular/core';
import { RestAPIService } from "./rest-api.service";
import { Observable } from "rxjs";
import { Image } from "../entity/image";
import { catchError } from "rxjs/operators";
import { HttpClient } from "../../../node_modules/@angular/common/http";

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

  getImages(): Observable<Image[]> {
    return this.http.get<Image[]>(this._URL + this.IMAGE_GET)
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

  getImageUrl( id: number ): string {
    return this._URL + this.IMAGE_GET + (id !== null ? '/' + id : '');
  }

  getImageFileUrl( id: number ): string {
    return this._URL + this.IMAGE_FILE + (id !== null ? '/' + id : '');
  }
}
