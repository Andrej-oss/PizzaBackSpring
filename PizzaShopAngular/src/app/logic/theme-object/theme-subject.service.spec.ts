import { TestBed } from '@angular/core/testing';

import { ThemeObjectService } from './theme-object.service';

describe('ThemeSubjectService', () => {
  let service: ThemeObjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ThemeObjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
