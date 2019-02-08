import { TagType } from "./tag-type.enum";

export class Tag {
  id: number;
  name: string;
  type: TagType;
  count: number;


  constructor( id: number, name: string, type: TagType, count: number ) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.count = count;
  }
}
