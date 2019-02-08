import { Component, OnInit } from '@angular/core';
import { ImageApiService } from "../../../services/image-api/image-api.service";
import { Image } from "../../../entities/image";
import { ImageGalleryComponent } from "../../../components/image-gallery/image-gallery.component";
import { ActivatedRoute, Router } from "@angular/router";
import { UrlParserService } from "../url-parser.service";
import { ImageOrder } from "../image-order.enum";

@Component({
  selector: 'app-search-images',
  templateUrl: './search-images.component.html',
  styleUrls: ['./search-images.component.less']
})
export class SearchImagesComponent implements OnInit {

  urlParser: UrlParserService;
  images: Image[];
  page: number;
  tags: number[];
  order: ImageOrder;
  pages = 1;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public imageApi: ImageApiService ) {
    this.urlParser = new UrlParserService(router, route);
    this.route.params.subscribe(() => this.onParamChange());
    this.route.queryParams.subscribe(() => this.onParamChange());
  }

  ngOnInit() {
    this.pageChange(this.page);
  }

  onParamChange() {
    this.page = this.urlParser.getPage();
    this.tags = this.urlParser.getTags();
    this.order = this.urlParser.getOrder();
    this.imageApi.getImages(ImageGalleryComponent.PER_PAGE, this.page, this.tags, this.order).subscribe(images => {
      this.images = images;
      if (images.length < ImageGalleryComponent.PER_PAGE) {
        this.pages = this.page;
      } else {
        this.pages = this.page + 1;
      }
    });
  }

  pageChange( page: number ) {
    this.urlParser.navigatePage(page);
  }

}
