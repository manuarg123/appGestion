import { TestBed } from '@angular/core/testing';

import { ServiceMedicalCenterService } from './service-medical-center.service';

describe('ServiceMedicalCenterService', () => {
  let service: ServiceMedicalCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceMedicalCenterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
