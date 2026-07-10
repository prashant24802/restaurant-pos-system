import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import {
  MatDialogModule,
  MatDialogRef
} from '@angular/material/dialog';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

import { Category } from '../models/category';
import { CategoryService } from '../services/category';
import { Menu } from '../services/menu';
import { MenuItemRequest } from '../models/menu-item-request';

@Component({
  selector: 'app-add-menu-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule
  ],
  templateUrl: './add-menu-dialog.html',
  styleUrl: './add-menu-dialog.css'
})
export class AddMenuDialog implements OnInit {

  private fb = inject(FormBuilder);

  private categoryService = inject(CategoryService);

  private menuService = inject(Menu);

  private dialogRef = inject(MatDialogRef<AddMenuDialog>);

  categories: Category[] = [];

  form = this.fb.group({

    name: ['', Validators.required],

    description: [''],

    price: [0, [Validators.required, Validators.min(0.01)]],

    imageUrl: [''],

    categoryId: [null as number | null, Validators.required]

  });

  ngOnInit(): void {

    this.loadCategories();

  }

  loadCategories(): void {

    this.categoryService.getAll().subscribe({

      next: (data) => {

        this.categories = data;

      },

      error: (err) => console.error(err)

    });

  }

  save(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    const request = this.form.value as MenuItemRequest;

    this.menuService.create(request).subscribe({

      next: () => {

        this.dialogRef.close(true);

      },

      error: (err) => {

        console.error(err);

        alert('Failed to create menu item');

      }

    });

  }

  cancel(): void {

    this.dialogRef.close(false);

  }

}