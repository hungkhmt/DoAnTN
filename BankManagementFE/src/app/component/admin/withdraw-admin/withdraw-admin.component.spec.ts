import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WithdrawAdminComponent } from './withdraw-admin.component';

describe('WithdrawAdminComponent', () => {
  let component: WithdrawAdminComponent;
  let fixture: ComponentFixture<WithdrawAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WithdrawAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WithdrawAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
