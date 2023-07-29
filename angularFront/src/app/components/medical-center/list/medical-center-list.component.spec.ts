import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalCenterListComponent } from './medical-center-list.component';

describe('MedicalCenterListComponent', () => {
  let component: MedicalCenterListComponent;
  let fixture: ComponentFixture<MedicalCenterListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedicalCenterListComponent]
    });
    fixture = TestBed.createComponent(MedicalCenterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
