import { TestBed } from '@angular/core/testing';

import { PizzaActionService } from './pizza-action.service';

describe('PizzaService', () => {
  let service: PizzaActionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PizzaActionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
