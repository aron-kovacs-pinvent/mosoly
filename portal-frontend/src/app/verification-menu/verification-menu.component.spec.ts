import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerificationMenuComponent } from './verification-menu.component';

describe('VerificationMenuComponent', () => {
  let component: VerificationMenuComponent;
  let fixture: ComponentFixture<VerificationMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerificationMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerificationMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
