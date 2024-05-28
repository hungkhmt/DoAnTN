import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountmanagementAdminComponent } from './accountmanagement-admin.component';

describe('AccountmanagementAdminComponent', () => {
  let component: AccountmanagementAdminComponent;
  let fixture: ComponentFixture<AccountmanagementAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountmanagementAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccountmanagementAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
