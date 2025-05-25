import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WithPatternComponent } from './with-pattern.component';

describe('WithPatternComponent', () => {
  let component: WithPatternComponent;
  let fixture: ComponentFixture<WithPatternComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WithPatternComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WithPatternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
