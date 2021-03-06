import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ImageOrder } from "./image-order.enum";
import { Tag } from "../../entities/tag";

@Injectable({
  providedIn: 'root'
})
export class UrlParserService {

  private static PAGE = 'page';
  private static TAGS = 'tags';
  private static ORDER = 'order';
  private static FILTERS = 'filters';

  constructor( private router: Router, private route: ActivatedRoute ) {

  }

  getPage(): number {
    const page = +this.route.snapshot.paramMap.get(UrlParserService.PAGE);
    if (!page) {
      return 1;
    }
    return page;
  }

  getTags(): number[] {
    let tags = this.route.snapshot.queryParams[UrlParserService.TAGS];
    if (!tags) {
      return null;
    }
    if (!Array.isArray(tags)) {
      tags = [tags];
    }
    return tags.map(tag => parseInt(tag.replace(/^(\d+)_.*/, '$1'), 10));
  }

  getFilters(): string[] {
    let filters = this.route.snapshot.queryParams[UrlParserService.FILTERS];
    if (!filters) {
      return null;
    }
    if (!Array.isArray(filters)) {
      filters = [filters];
    }
    return filters;
  }

  getOrder(): ImageOrder {
    const order = this.route.snapshot.queryParamMap.get(UrlParserService.ORDER);
    if (order && ImageOrder[order]) {
      return ImageOrder[order];
    }
    return ImageOrder.NEWEST;
  }

  navigate( page: number, tags: Tag[] = null, order: ImageOrder = ImageOrder.NEWEST, filters: string[] = null ): void {
    this.router.navigate(['/' + page], {
      queryParams: {
        tags: tags.map(tag => tag.id + '_' + tag.name),
        filters: filters,
        order: order
      }
    });
  }

  navigatePage( page: number ): void {
    this.router.navigate(['/' + page], {queryParams: this.route.snapshot.queryParams});
  }
}
