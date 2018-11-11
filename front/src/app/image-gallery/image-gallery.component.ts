import { Component, Input, OnInit } from '@angular/core';
import { Image } from "../entity/image";
import { RestAPIService } from "../service/rest-api.service";

@Component({
  selector: 'app-image-gallery',
  templateUrl: './image-gallery.component.html',
  styleUrls: ['./image-gallery.component.less']
})
export class ImageGalleryComponent implements OnInit {

  @Input() images: Image[] = [];
  public TMP_WIDTH = 350;
  public TMP_HEIGHT = 180;

  constructor( public restApi: RestAPIService) {

  }

  ngOnInit() {
  }

}
