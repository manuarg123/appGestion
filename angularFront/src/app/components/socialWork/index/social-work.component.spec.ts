import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialWorkComponent } from './social-work.component';

describe('SocialWorkComponent', () => {
  let component: SocialWorkComponent;
  let fixture: ComponentFixture<SocialWorkComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocialWorkComponent]
    });
    fixture = TestBed.createComponent(SocialWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
