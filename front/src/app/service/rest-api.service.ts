import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RestAPIService {

  private _URL = 'http://localhost:8080/rurybooru/rest/';
  private IMAGE_TMP = 'image/tmp/';
  private IMAGE_OPEN = 'image/open/';

  constructor() {
  }

  /** URLs */
  getImageThumbUrl( id: number, width: number, height: number ): string {
    return this._URL + this.IMAGE_TMP + id + '?width=' + width + '&height=' + height;
  }

  getOpenImageUrl( id: number ): string {
    return this._URL + this.IMAGE_OPEN + id;
  }

  get URL(): string {
    return this._URL;
  }
}
