import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsermanagementAdminComponent } from './usermanagement-admin.component';

describe('UsermanagementAdminComponent', () => {
  let component: UsermanagementAdminComponent;
  let fixture: ComponentFixture<UsermanagementAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsermanagementAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UsermanagementAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
