import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ABMsComponent } from './abms.component';

describe('ABMsComponent', () => {
  let component: ABMsComponent;
  let fixture: ComponentFixture<ABMsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ABMsComponent]
    });
    fixture = TestBed.createComponent(ABMsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
