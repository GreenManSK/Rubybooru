import { TestBed, inject } from '@angular/core/testing';

import { TagApiService } from './tag-api.service';

describe('TagApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TagApiService]
    });
  });

  it('should be created', inject([TagApiService], (service: TagApiService) => {
    expect(service).toBeTruthy();
  }));
});
