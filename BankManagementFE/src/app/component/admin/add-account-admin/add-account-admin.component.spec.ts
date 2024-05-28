import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAccountAdminComponent } from './add-account-admin.component';

describe('AddAccountAdminComponent', () => {
  let component: AddAccountAdminComponent;
  let fixture: ComponentFixture<AddAccountAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAccountAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddAccountAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
