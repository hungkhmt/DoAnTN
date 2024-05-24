import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepositAdminComponent } from './deposit-admin.component';

describe('DepositAdminComponent', () => {
  let component: DepositAdminComponent;
  let fixture: ComponentFixture<DepositAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepositAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DepositAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
