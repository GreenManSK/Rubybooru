import { Component, Input, OnInit } from '@angular/core';
import { Image } from "../../entities/image";
import { ImageApiService } from "../../services/image-api/image-api.service";
import { environment } from "../../../environments/environment";

@Component({
  selector: 'app-image-gallery',
  templateUrl: './image-gallery.component.html',
  styleUrls: ['./image-gallery.component.less']
})
export class ImageGalleryComponent implements OnInit {

  public static PER_PAGE = environment.imagesPerPage;
  @Input() images: Image[] = [];
  public TMP_WIDTH = 350;
  public TMP_HEIGHT = 180;

  constructor( public imageApi: ImageApiService ) {

  }

  ngOnInit() {
  }

  openImage( id: number ) {
    this.imageApi.openImage(id).subscribe(r => r);
    return false;
  }

}
