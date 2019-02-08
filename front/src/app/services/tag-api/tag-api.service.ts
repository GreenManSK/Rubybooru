import { Injectable } from '@angular/core';
import { RestAPIService } from "../rest-api/rest-api.service";
import { HttpClient } from "../../../../node_modules/@angular/common/http";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { Tag } from "../../entities/tag";

@Injectable({
  providedIn: 'root'
})
export class TagApiService extends RestAPIService {


  private TAG_GET = 'tag';

  constructor( public http: HttpClient ) {
    super(http);
  }

  getTag( id: number ): Observable<Tag> {
    return this.http.get<Tag>(this.getTagUrl(id)).pipe(
      catchError(this.handleError<Tag>('getTag(' + id + ')', null))
    );
  }

  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.getAllUrl()).pipe(
      catchError(this.handleError<Tag[]>('getAll()', null))
    );
  }

  /** URLs */
  getTagUrl( id: number ): string {
    return this._URL + this.TAG_GET + '/' + id;
  }

  getAllUrl(): string {
    return this._URL + this.TAG_GET + '/';
  }
}
