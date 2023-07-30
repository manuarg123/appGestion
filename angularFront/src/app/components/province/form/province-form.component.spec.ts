import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProvinceFormComponent } from './province-form.component';

describe('ProvinceFormComponent', () => {
  let component: ProvinceFormComponent;
  let fixture: ComponentFixture<ProvinceFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProvinceFormComponent]
    });
    fixture = TestBed.createComponent(ProvinceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
