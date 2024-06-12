import { Component, INJECTOR, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatDialogRef,
  MatDialogActions,
  MatDialogClose,
  MatDialogTitle,
  MatDialogContent,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button'
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatRadioModule} from '@angular/material/radio';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import { UserService } from '../../../service/user.service';
import { AuthService } from '../../../service/auth.service';
import { PasswordMatchValidator } from '../../../checkpassword.validator';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent,
    MatInputModule, MatFormFieldModule, MatRadioModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {
  passwordForm = this.fb.group({
    oldPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
    confirmNewPassword: ['', Validators.required]
  }, {
    validators: PasswordMatchValidator // Áp dụng validator
  });

  token = sessionStorage.getItem('authToken');
  constructor(private fb: FormBuilder, private userService: UserService, private dilogRef: MatDialogRef<ChangePasswordComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any, private authService: AuthService) {
      
     }

  onFormSubmit() {
    if (this.passwordForm.invalid) {
      return; // Nếu form không hợp lệ, không tiếp tục
    }
    
    let { confirmNewPassword, ...formValues } = this.passwordForm.value;
    console.log(formValues);
    this.userService.changePassword(formValues).subscribe({
      next: (val: any) => {
        alert("Change Password Successful. Please Log in again!!!");
        this.dilogRef.close(true);
        this.authService.logout(this.token!);
      }, error: (err: any) => {
        console.log(err)
      }
    });
  }
  

}
