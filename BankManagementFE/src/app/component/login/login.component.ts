import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { User } from '../../model/user';
import { AuthService } from '../../service/auth.service';
import { AccountService } from '../../service/account.service';

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
    private authService: AuthService,
    private accountService: AccountService) {
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
