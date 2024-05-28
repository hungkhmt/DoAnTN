import { Component, OnInit, Inject, Renderer2, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DOCUMENT } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../service/auth.service';
import { User } from '../../model/user';
import { nextTick } from 'process';
import { UserService } from '../../service/user.service';
import { response } from 'express';
import { Registration } from '../../model/registration';
import { ToastrModule } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    ToastrModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {
  loginForm = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  signUpForm = this.fb.group({
    fullname: ['', [Validators.required]],
    dateOfBirth: ['', [Validators.required]],
    phoneNumber: ['', [Validators.required]],
    address: ['', [Validators.required]],
    username: ['', [Validators.required]],
    email: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  })

  isLogin: boolean = true;

  constructor(private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService,
    @Inject(DOCUMENT) private document: Document) {}

  container: HTMLElement | null = null;

  ngOnInit(): void {
    // Get the container element after view initialization
    this.container = this.document.getElementById('container');
    if (this.container) {
      this.container.classList.add('sign-in');
    } else {
      console.error('Container element not found');
    }
  }

  toggle(): void {
    if (this.container) {
      this.container.classList.toggle('sign-in');
      this.container.classList.toggle('sign-up');
    } else {
      console.error('Container element not found');
    }
  }

  userInfo : User | undefined
  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password']
  }

  loginUser() {
    const { username, password } = this.loginForm.value;
    this.authService.login(username!, password!).subscribe({
      next: (respone: any) => {
        let role = this.authService.getRole();
        if(!role) {
          this.router.navigate(['/forbidden'])
          this.isLogin = false;
        }
        if(role === "USER") {
          this.isLogin = true;
          this.router.navigate(['/user/homepage']);
        } else {
          this.isLogin = true;
          this.router.navigate(['/admin/homepage']);
        }
      },
      error: (err: any) => {
        let notification = this.document.getElementById('nameErrorMessage');
        if (notification) {
          notification.style.display = 'block';
        } else {
          console.error('Notification element not found');
        }
        console.log(err);
      }
    })
  }

  showSuccessMessage: boolean = false;
  showErrorMessage: boolean = false;
  errorRegistrationMessage: string = 'Registration Failed';
  successRegistrationMessage: string = 'Registration Successful';

  registration() {
    let formValues = { ...this.signUpForm.value };
    formValues.dateOfBirth = this.formatDate(this.signUpForm.value.dateOfBirth!); // Chuyển đổi định dạng ngày tháng

    this.userService.registration(formValues).subscribe({
      next: (response: any) => {
        this.showSuccessMessage = true;
        this.showErrorMessage = false;
        setTimeout(() => this.showSuccessMessage = false, 3000); // Ẩn thông báo sau 3 giây
        this.toggle();
      },
      error: (err: any) => {
        this.showErrorMessage = true;
        this.showSuccessMessage = false;
        setTimeout(() => this.showErrorMessage = false, 3000); // Ẩn thông báo sau 3 giây
      }
    });
  }
  
  formatDate(date: string): string {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();
  
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
  
    return [day, month, year].join('/');
  }
  
  

}
