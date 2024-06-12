import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/select';
import {MatCardModule} from '@angular/material/card';
import { UserService } from '../../../service/user.service';
import { User } from '../../../model/user';
import { AuthService } from '../../../service/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';
import { ChangePasswordComponent } from '../change-password/change-password.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule, MatCardModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  constructor(private userService: UserService, private authService: AuthService, public _dialog: MatDialog) {};
  
  user: User | undefined;

  phoneNumber: string | undefined;

  userId = this.authService.getUserId();

  ngOnInit() {
    this.getUserById(this.userId);
  }

  getUserById(id: any) {
    this.userService.getUserById(id).subscribe({
      next: (val: any) => {
        this.user = val;
        this.phoneNumber = this.transform(this.user?.phoneNumber);
      }, error: (err: any) => {
        console.log(err)
      }
    })
  }

  transform(phoneNumber: any): string {
    return `${phoneNumber.substr(0, 3)}***${phoneNumber.substr(-3)}`;
  }

  openEditButton() {
    let userTmp = {...this.user};
    // userTmp.dateOfBirth = this.convertDateFormat(this.user?.dateOfBirth!);
    this.openEditForm(userTmp);
  }

  openEditForm(data: any) {
    const dialogRef = this._dialog.open(EditProfileComponent, {
      data,
    })
    dialogRef.afterClosed().subscribe({
      next: (res: any) => {
        this.getUserById(this.userId);
        location.reload();
      }, error: (err) => {
        alert('fail')
      }
    })
  }

  openChangePasswordForm() {
    const dialogRef = this._dialog.open(ChangePasswordComponent)
    dialogRef.afterClosed().subscribe({
      next: (res: any) => {
        this.getUserById(this.userId);
        location.reload();
      }, error: (err) => {
        alert('fail')
      }
    })
  }

  convertDateFormat(dateString: string): string {
    const [day, month, year] = dateString.split('/');
    return `${month}/${day}/${year}`;
  }

}
