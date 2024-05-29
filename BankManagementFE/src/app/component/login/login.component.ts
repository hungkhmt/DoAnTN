import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { User } from '../../model/user';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,
    HttpClientModule,
    ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  isLogin: boolean = true;

  constructor(private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService) {
    }

    userInfo : User | undefined
  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password']
  }

  // loginUser() {
  //   const { username, password } = this.loginForm.value;
  //   this.authService.getUserByUsername(username as string).subscribe(
  //     response => {      
  //       if (response[0] !== undefined && response[0].password === password) {
  //         localStorage.setItem('username', username as string);
  //         localStorage.setItem('role', response[0].role as string);
  //         localStorage.setItem('name', response[0].name);
  //         localStorage.setItem('password', response[0].password);
  //         localStorage.setItem("id", response[0].id);
  //         localStorage.setItem("isLoggedIn", "true");
  //         this.isLogin = true;
  //         this.router.navigate(['']);
  //       } else {
  //         localStorage.setItem("isLoggedIn", "true");
  //         this.isLogin = false;
  //       }
  //     }      
  //   )    
  // }

  loginUser() {
    const { username, password } = this.loginForm.value;
    this.authService.login(username!, password!).subscribe(
      respone => {
        let role = this.authService.getRole();
        if(!role) {
          this.router.navigate(['/forbidden'])
          this.isLogin = false;
        }
        if(role && role == "ADMIN") {
          this.isLogin = true;
          this.router.navigate(['/admin']);
        } else {
          this.isLogin = true;
          this.router.navigate(['/user']);
        }
        
      }
    )
  }
  
}
