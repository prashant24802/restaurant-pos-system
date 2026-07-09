import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Auth } from '../../../core/services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  private fb = inject(FormBuilder);
  private auth = inject(Auth);

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  login() {

    if (this.loginForm.invalid) {
      return;
    }

    this.auth.login(this.loginForm.value as any)
      .subscribe({

        next: (response) => {

          localStorage.setItem("token", response.token);

          alert("Login Successful");

          console.log(response);

        },

        error: (error) => {

          console.error(error);

          alert("Invalid Credentials");

        }

      });

  }

}