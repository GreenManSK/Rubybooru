import { Tag } from "../../entities/tag";
import { TagType } from "../../entities/tag-type.enum";

export class InputParser {
  private _tags: Tag[];
  private _filters: string[];

  constructor( values: string[], nameToId: object ) {
    this.parse(values, nameToId);
  }

  private parse( values: string[], nameToId: object ): void {
    this._tags = [];
    this._filters = [];

    if (values.length === 0) {
      return null;
    }
    const tags = [];
    for (const v of values) {
      if (nameToId.hasOwnProperty(v)) {
        tags.push(new Tag(nameToId[v], v, TagType.GENERAL, 0));
      } else {
        this._filters.push(v);
      }
    }
  }

  getTags(): Tag[] {
    return this._tags;
  }

  getFilters(): string[] {
    return this._filters;
  }
}
