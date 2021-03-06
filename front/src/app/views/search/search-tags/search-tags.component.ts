import { Component, OnInit } from '@angular/core';
import { Tag } from "../../../entities/tag";
import { ActivatedRoute, Router } from "@angular/router";
import { ImageApiService } from "../../../services/image-api/image-api.service";
import { TagApiService } from "../../../services/tag-api/tag-api.service";
import { TagType } from "../../../entities/tag-type.enum";
import { UrlParserService } from "../url-parser.service";
import { ImageOrder } from "../image-order.enum";
import { environment } from "../../../../environments/environment";

@Component({
  selector: 'app-search-tags',
  templateUrl: './search-tags.component.html',
  styleUrls: ['./search-tags.component.less']
})
export class SearchTagsComponent implements OnInit {

  private static TAGS_FROM_PAGES = environment.imagesForMenuTags;
  private MAX_TAGS_SHOWN = {};

  public tags: Tag[] = [];
  urlParser: UrlParserService;
  urlTags: number[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public imageApi: ImageApiService,
    public tagApi: TagApiService
  ) {
    this.urlParser = new UrlParserService(router, route);
    this.route.queryParams.subscribe(() => this.getMostCommonTags());

    for (const t of Object.keys(TagType)) {
      this.MAX_TAGS_SHOWN[t] = 2;
    }
    this.MAX_TAGS_SHOWN['COPYRIGHT'] = 5;
    this.MAX_TAGS_SHOWN['CHARACTER'] = 5;
    this.MAX_TAGS_SHOWN['GENERAL'] = 25;
  }

  ngOnInit() {
    this.getMostCommonTags();
  }

  getMostCommonTags() {
    this.urlTags = this.urlParser.getTags();
    this.imageApi.getImages(SearchTagsComponent.TAGS_FROM_PAGES, 1, this.urlTags, ImageOrder.NEWEST).subscribe(images => {
      const counting = {};
      for (const key of Object.keys(TagType)) {
        counting[key] = [];
      }
      const idMap = {};
      for (const image of images) {
        for (const tag of image.tags) {
          if (!counting[tag.type][tag.id]) {
            counting[tag.type][tag.id] = 1;
            idMap[tag.id] = tag;
          } else {
            counting[tag.type][tag.id]++;
          }
        }
      }
      this.tags = [];
      for (const counter of Object.keys(counting)) {
        const typeCounts = counting[counter];
        const sortedIds = Object.keys(typeCounts).sort(function ( a, b ) {
          return typeCounts[a] - typeCounts[b];
        });
        for (const id of sortedIds.slice(0, this.MAX_TAGS_SHOWN[counter])) {
          this.tags.push(idMap[id]);
        }
      }
      this.addTagCounts();
    });
  }

  addTagCounts() {
    for (const tag of this.tags) {
      this.tagApi.getTag(tag.id).subscribe(t => {
        tag.count = t.count;
      });
    }
  }

}
