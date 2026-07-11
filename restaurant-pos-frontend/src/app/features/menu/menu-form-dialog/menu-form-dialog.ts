import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import {
  MAT_DIALOG_DATA,
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
import { MenuItem } from '../models/menu-item';
import { MenuItemRequest } from '../models/menu-item-request';

@Component({
  selector: 'app-menu-form-dialog',
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
  templateUrl: './menu-form-dialog.html',
  styleUrl: './menu-form-dialog.css'
})
export class MenuFormDialog implements OnInit {

  private fb = inject(FormBuilder);

  private categoryService = inject(CategoryService);

  private menuService = inject(Menu);

  private dialogRef = inject(MatDialogRef<MenuFormDialog>);

  private data = inject<MenuItem | null>(MAT_DIALOG_DATA, {
    optional: true
  });

  categories: Category[] = [];

  isEdit = false;

  form = this.fb.group({

    name: ['', Validators.required],

    description: [''],

    price: [0, [Validators.required, Validators.min(0.01)]],

    imageUrl: [''],

    categoryId: [null as number | null, Validators.required]

  });

  ngOnInit(): void {

    this.loadCategories();

    if (this.data) {

      this.isEdit = true;

      this.form.patchValue({

        name: this.data.name,

        description: this.data.description,

        price: this.data.price,

        imageUrl: this.data.imageUrl,

        categoryId: this.data.categoryId

      });

    }

  }

  loadCategories(): void {

    this.categoryService.getAll().subscribe({

      next: (data) => {

        this.categories = data;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  save(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    const request = this.form.value as MenuItemRequest;

    if (this.isEdit && this.data) {
      this.menuService.create(request).subscribe({

      next: () => {

        this.dialogRef.close(true);

      },

      error: (err) => {

        console.error(err);

        alert('Failed to create menu item');

      }

    });

  }else {

      this.menuService.update(this.data!.id, request).subscribe({
        next: () => {

          this.dialogRef.close(true);

        },

        error: (err) => {

          console.error(err);

          alert('Failed to create menu item');

        }

      });

    }

  }

  cancel(): void {

    this.dialogRef.close(false);

  }

}