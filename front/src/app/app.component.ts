import { Component } from '@angular/core';
import { Tag } from "./entity/tag";
import { TagType } from "./entity/tag-type.enum";
import { Image } from "./entity/image";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  test: Tag[] = [
    new Tag(10, "Test tag", TagType.GENERAL, 128),
    new Tag(10, "Test tag", TagType.GENERAL, 128),
    new Tag(10, "Test tag", TagType.GENERAL, 128),
    new Tag(10, "Test tag", TagType.GENERAL, 128),
    new Tag(10, "Test tag", TagType.GENERAL, 128),
    new Tag(10, "Test tag", TagType.GENERAL, 128),
    new Tag(10, "Test tag", TagType.GENERAL, 128),
  ];

  images: Image[] = [
    new Image(1, "test"),
    new Image(2, "test"),
    new Image(3, "test"),
    new Image(4, "test"),
    new Image(5, "test"),
    new Image(7, "test"),
    new Image(8, "test"),
    new Image(25, "test"),
    new Image(45, "test"),
    new Image(644, "test"),
  ];
}
