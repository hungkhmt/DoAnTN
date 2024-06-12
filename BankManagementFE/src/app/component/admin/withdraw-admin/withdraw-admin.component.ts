import { Component, OnInit } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TranferService } from '../../../service/tranfer.service';
import { User } from '../../../model/user';
import { UserService } from '../../../service/user.service';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-withdraw-admin',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule, MatCardModule, ReactiveFormsModule],
  templateUrl: './withdraw-admin.component.html',
  styleUrl: './withdraw-admin.component.css'
})
export class WithdrawAdminComponent {
  tranferForm = new FormGroup({
    accountId: new FormControl(),
    amount: new FormControl(''),
    description: new FormControl('')
  });
  

  user: User | undefined;
  fullname: string | undefined;
  accountId = sessionStorage.getItem('currentAccount');
  balance: number | undefined;
  formattedBalance: string | undefined;
  userId = this.authService.getUserId();
  accounts: any | undefined;

  constructor(
    private tranferService: TranferService,
    private userService: UserService,
    private accountService: AccountService,
    private authService: AuthService
  ) {}
  

  onFormSubmit() {
    this.tranferService.withDraw(this.tranferForm.value).subscribe({
      next: (val: any) => {
        alert("Withdraw Successful");
        console.log("tranfer ok");
        this.tranferForm.reset();
        location.reload();
      },
      error: (err: any) => {
        alert("Giao dịch không thành công!!!");
        console.log(err);
        this.tranferForm.reset();  // Reset the form here as well
      }
    });
  }

}
