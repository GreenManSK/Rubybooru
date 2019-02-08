import { Tag } from "./tag";

export class Image {
  id: number;
  name: string;
  date: Date;
  width: number;
  height: number;
  source: string;
  infoSource: string;
  tags: Tag[];
  // parent: Dir;


  constructor( id: number, name: string ) {
    this.id = id;
    this.name = name;
  }
}
