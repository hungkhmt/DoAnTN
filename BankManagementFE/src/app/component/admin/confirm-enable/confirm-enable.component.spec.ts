import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmEnableComponent } from './confirm-enable.component';

describe('ConfirmEnableComponent', () => {
  let component: ConfirmEnableComponent;
  let fixture: ComponentFixture<ConfirmEnableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmEnableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfirmEnableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
