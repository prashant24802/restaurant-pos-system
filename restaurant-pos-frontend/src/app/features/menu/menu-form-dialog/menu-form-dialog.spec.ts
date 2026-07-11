import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuFormDialog } from './menu-form-dialog';

describe('MenuFormDialog', () => {
  let component: MenuFormDialog;
  let fixture: ComponentFixture<MenuFormDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuFormDialog],
    }).compileComponents();

    fixture = TestBed.createComponent(MenuFormDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
