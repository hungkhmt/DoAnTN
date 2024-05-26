import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/select';
import {MatCardModule} from '@angular/material/card';
import { UserService } from '../../service/user.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule, MatCardModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  constructor(private userService: UserService) {};
  
  user: User | undefined;

  phoneNumber: string | undefined;

  ngOnInit() {
    this.getUserById(1);
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

}
