import { Component, OnInit } from '@angular/core';
import { Image } from "../../../entities/image";
import { ActivatedRoute } from "@angular/router";
import { Tag } from "../../../entities/tag";
import { ImageApiService } from "../../../services/image-api/image-api.service";
import { TagApiService } from "../../../services/tag-api/tag-api.service";

@Component({
  selector: 'app-image-panel',
  templateUrl: './image-panel.component.html',
  styleUrls: ['./image-panel.component.less']
})
export class ImagePanelComponent implements OnInit {

  image: Image;
  tags: Tag[] = [];

  constructor( private route: ActivatedRoute, public imageApi: ImageApiService, public tagApi: TagApiService ) {

  }

  ngOnInit() {
    this.getImage();
  }

  getImage(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.imageApi.getImage(id).subscribe(image => {
      this.image = image;
      for (const tag of image.tags) {
        this.tagApi.getTag(tag.id).subscribe(t => {
          this.tags.push(t);
        });
      }
    });
  }

  shortenSource( url: string ): string {
    return url.replace(/^https?:\/\/(www\.)?/, '');
  }

  openImage() {
    this.imageApi.openImage(this.image.id).subscribe(r => r);
    return false;
  }

}
