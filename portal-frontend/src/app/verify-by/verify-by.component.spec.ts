import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyByComponent } from './verify-by.component';

describe('VerifyByComponent', () => {
  let component: VerifyByComponent;
  let fixture: ComponentFixture<VerifyByComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerifyByComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerifyByComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
