import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialWorkFormComponent } from './social-work-form.component';

describe('SocialWorkFormComponent', () => {
  let component: SocialWorkFormComponent;
  let fixture: ComponentFixture<SocialWorkFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocialWorkFormComponent]
    });
    fixture = TestBed.createComponent(SocialWorkFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
