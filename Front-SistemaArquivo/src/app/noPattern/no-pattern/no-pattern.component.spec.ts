import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoPatternComponent } from './no-pattern.component';

describe('NoPatternComponent', () => {
  let component: NoPatternComponent;
  let fixture: ComponentFixture<NoPatternComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoPatternComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoPatternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
