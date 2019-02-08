import { Injectable } from '@angular/core';
import { Tag } from "../../entities/tag";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  static typesSort = environment.tagTypeOrder;

  constructor() {
  }

  sortTags( tags: Tag[] ): Tag[] {
    return tags.sort(( a: any, b: any ) => {
      if (a.type === b.type) {
        return b.count - a.count;
      }
      return TagService.typesSort.indexOf(a.type.toLocaleLowerCase())
      < TagService.typesSort.indexOf(b.type.toLocaleLowerCase()) ? -1 : 1;
    });
  }

}
