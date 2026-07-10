import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMenuDialog } from './add-menu-dialog';

describe('AddMenuDialog', () => {
  let component: AddMenuDialog;
  let fixture: ComponentFixture<AddMenuDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMenuDialog],
    }).compileComponents();

    fixture = TestBed.createComponent(AddMenuDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
