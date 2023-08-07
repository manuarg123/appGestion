import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialWorkListComponent } from './social-work-list.component';

describe('SocialWorkListComponent', () => {
  let component: SocialWorkListComponent;
  let fixture: ComponentFixture<SocialWorkListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocialWorkListComponent]
    });
    fixture = TestBed.createComponent(SocialWorkListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
