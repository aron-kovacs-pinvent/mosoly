import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyMembershipComponent } from './apply-membership.component';

describe('ApplyMembershipComponent', () => {
  let component: ApplyMembershipComponent;
  let fixture: ComponentFixture<ApplyMembershipComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplyMembershipComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplyMembershipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
