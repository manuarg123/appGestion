import { TestBed } from '@angular/core/testing';

import { ServiceProvinceService } from './service-province.service';

describe('ServiceProvinceService', () => {
  let service: ServiceProvinceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceProvinceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
