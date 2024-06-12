import { Component, INJECTOR, Inject } from '@angular/core';
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
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import { UserService } from '../../../service/user.service';
import { AuthService } from '../../../service/auth.service';
import { User } from '../../../model/user';

@Component({
  selector: 'app-edit-profile',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent,
    MatInputModule, MatFormFieldModule, MatRadioModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent {
  userForm = new FormGroup({
    id: new FormControl(''),
    username: new FormControl(''),
    email: new FormControl(''),
    fullname: new FormControl(''),
    dateOfBirth: new FormControl(''),
    phoneNumber: new FormControl(''),
    address: new FormControl('')
  });

  user: User[] | undefined;
  id = this.authService.getUserId();

  constructor(private userService: UserService, private authService: AuthService, private dilogRef: MatDialogRef<EditProfileComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    this.userForm.patchValue(this.data)
  }

  onFormSubmit() {
    let formValues = {...this.userForm.value};
    formValues.dateOfBirth = this.formatDate(this.userForm.value.dateOfBirth!);
    formValues.id = this.id;
    this.userService.editUser(formValues).subscribe({
      next: (val: any) => {
        alert("Update User Successful")
        this.dilogRef.close(true);
      }, error: (err: any) => {
        console.log(err)
      }
    })
  }

  formatDate(date: string): string {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();
  
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
  
    return [month, day, year].join('/');
  }

}
