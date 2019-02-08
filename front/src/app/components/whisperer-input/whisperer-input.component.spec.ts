import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WhispererInputComponent } from './whisperer-input.component';

describe('WhispererInputComponent', () => {
  let component: WhispererInputComponent;
  let fixture: ComponentFixture<WhispererInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WhispererInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WhispererInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
