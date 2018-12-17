import { Tag } from "../entity/tag";

export class WhispererTag {
  tag: Tag;
  words: string[];


  constructor( tag: Tag ) {
    this.tag = tag;
    this.words = this.getWords(tag.name);
  }

  match( prefix: string ): boolean {
    for (const word of this.words) {
      if (word.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  getWords( name: string ): string[] {
    const words = name.split('_');
    words.push(name);
    return words;
  }
}
