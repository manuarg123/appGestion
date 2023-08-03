import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalCenterFormComponent } from './medical-center-form.component';

describe('MedicalCenterFormComponent', () => {
  let component: MedicalCenterFormComponent;
  let fixture: ComponentFixture<MedicalCenterFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedicalCenterFormComponent]
    });
    fixture = TestBed.createComponent(MedicalCenterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
