// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.test`.

import { TagType } from "../app/entities/tag-type.enum";

export const environment = {
  production: false,
  // API Url
  restUrl: 'http://localhost:8080/rurybooru/rest/',

  // Image counts
  imagesPerPage: 24,
  imagesForMenuTags: 24,
  defaultPaginationSize: 7,

  // Whisperer settings
  whispererTagLimit: 10,
  whispererUsedTags: [TagType.COPYRIGHT, TagType.CHARACTER, TagType.ARTIST, TagType.GENERAL],

  // Tag sorting
  tagTypeOrder: [
    TagType.COPYRIGHT,
    TagType.CHARACTER,
    TagType.GENERAL,
    TagType.ARTIST,
    TagType.CIRCLE,
    TagType.STUDIO,
    TagType.META,
    TagType.MEDIUM,
    TagType.STYLE,
    TagType.SOURCE,
    TagType.FAULTS
  ]
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
