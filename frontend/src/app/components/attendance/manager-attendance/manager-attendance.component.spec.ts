import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerAttendanceComponent } from './manager-attendance.component';

describe('ManagerAttendanceComponent', () => {
  let component: ManagerAttendanceComponent;
  let fixture: ComponentFixture<ManagerAttendanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManagerAttendanceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerAttendanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
