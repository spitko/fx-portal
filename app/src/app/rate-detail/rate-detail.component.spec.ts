import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateDetailComponent } from './rate-detail.component';

describe('RateDetailComponent', () => {
  let component: RateDetailComponent;
  let fixture: ComponentFixture<RateDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RateDetailComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RateDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
