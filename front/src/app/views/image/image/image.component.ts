import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Image } from "../../../entities/image";
import { ImageApiService } from "../../../services/image-api/image-api.service";

@Component({
  selector: 'app-image',
  templateUrl: './image.component.html',
  styleUrls: ['./image.component.less']
})
export class ImageComponent implements OnInit {

  image: Image;

  constructor( private route: ActivatedRoute, public imageApi: ImageApiService ) {
  }

  ngOnInit() {
    this.getImage();
  }

  getImage(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.imageApi.getImage(id).subscribe(image => this.image = image);
  }

  toggleMini( e: Element ) {
    if (e.classList.contains('mini')) {
      e.classList.remove('mini');
    } else {
      e.classList.add('mini');
    }
  }
}
