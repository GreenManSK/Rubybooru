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
    if (this.filterChanged(this.urlParser.getTags())) {
      this.imageApi.getImageCount(this.urlParser.getTags()).subscribe(count => {
        this.pages = Math.ceil(count / ImageGalleryComponent.PER_PAGE);
      });
    }

    this.page = this.urlParser.getPage();
    this.tags = this.urlParser.getTags();
    this.order = this.urlParser.getOrder();
    this.imageApi.getImages(ImageGalleryComponent.PER_PAGE, this.page, this.tags, this.order).subscribe(images => {
      this.images = images;
    });
  }

  pageChange( page: number ) {
    this.urlParser.navigatePage(page);
  }

  filterChanged( tags: number[] ): boolean {
    return JSON.stringify(tags) !== JSON.stringify(this.tags);
  }

}
