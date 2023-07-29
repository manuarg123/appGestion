import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalCenterComponent } from './medical-center.component';

describe('MedicalCenterComponent', () => {
  let component: MedicalCenterComponent;
  let fixture: ComponentFixture<MedicalCenterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedicalCenterComponent]
    });
    fixture = TestBed.createComponent(MedicalCenterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
