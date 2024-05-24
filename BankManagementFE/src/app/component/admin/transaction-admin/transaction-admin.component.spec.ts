import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionAdminComponent } from './transaction-admin.component';

describe('TransactionAdminComponent', () => {
  let component: TransactionAdminComponent;
  let fixture: ComponentFixture<TransactionAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TransactionAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
