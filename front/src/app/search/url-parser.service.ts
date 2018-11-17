import { Injectable } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { ImageOrder } from "./image-order.enum";
import { Tag } from "../entity/tag";

@Injectable({
  providedIn: 'root'
})
export class UrlParserService {

  private static PAGE = 'page';
  private static TAGS = 'tags';
  private static ORDER = 'order';

  constructor( private route: ActivatedRoute ) {

  }

  getPage(): number {
    const page = +this.route.snapshot.paramMap.get(UrlParserService.PAGE);
    if (!page) {
      return 1;
    }
    return page;
  }

  getTags(): number[] {
    const tags = this.route.snapshot.queryParamMap.get(UrlParserService.TAGS);
    if (!tags) {
      return null;
    }
    return tags.split('-').map(tag => parseInt(tag.replace(/^(\d+)_.*/, '$1'), 10));
  }

  getOrder(): ImageOrder {
    const order = this.route.snapshot.queryParamMap.get(UrlParserService.ORDER);
    if (order && ImageOrder[order]) {
      return ImageOrder[order];
    }
    return ImageOrder.NEWEST;
  }

  generateUrl( page: number, tags: Tag[] = null, order: ImageOrder = ImageOrder.NEWEST ): string {
    let query = '';
    if (tags != null) {
      query = '?tags=' + this.tagsToString(tags);
    }
    if (order !== ImageOrder.NEWEST) {
      query += query === '' ? '?' : '&';
      query += 'order=' + order;
    }
    return '/' + page + query;
  }

  changePage( page: number ) {
    let query = '?';
    const params = this.route.snapshot.queryParamMap;
    for (const p of params.keys) {
      if (query !== '?') {
        query += '&';
      }
      query += p + '=' + params.get(p);
    }
    return '/' + page + (query !== '?' ? query : '');
  }

  private tagsToString( tags: Tag[] ): string {
    let result = '';
    for (const t of tags) {
      if (result !== '') {
        result += '-';
      }
      result += t.id + '_' + t.name;
    }
    return encodeURI(result);
  }
}
