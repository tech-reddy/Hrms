import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayrollGenerateComponent } from './payroll-generate.component';

describe('PayrollGenerateComponent', () => {
  let component: PayrollGenerateComponent;
  let fixture: ComponentFixture<PayrollGenerateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PayrollGenerateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PayrollGenerateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
