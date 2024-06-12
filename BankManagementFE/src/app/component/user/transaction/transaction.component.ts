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
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule, MatCardModule, ReactiveFormsModule],
  providers: [DecimalPipe],
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {
  tranferForm = new FormGroup({
    sourceAccountId: new FormControl(),
    destinationAccountId: new FormControl(''),
    destinationAccountName: new FormControl(''),
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
    private authService: AuthService,
    private decimalPipe: DecimalPipe
  ) {}

  ngOnInit() {
    this.getUserByUserId();
    this.getAccountByAccId()
  
    // Lắng nghe sự thay đổi giá trị của destinationAccountId
    this.tranferForm.get('destinationAccountId')?.valueChanges.subscribe(accId => {
      if (accId) {
        this.accountService.getUserByAccId(accId).subscribe({
          next: (response: any) => {
            this.tranferForm.get('destinationAccountName')?.setValue(response.fullname);
          },
          error: (err: any) => {
            console.log("error: ", err);
          }
        });
      } else {
        this.tranferForm.get('destinationAccountName')?.reset();
      }
    });
  }
  

  onFormSubmit() {
    const formValue = { ...this.tranferForm.value };
    delete formValue.destinationAccountName;
  
    formValue.sourceAccountId = this.accountId!;
    this.tranferService.tranferMoney(formValue).subscribe({
      next: (val: any) => {
        alert("Tranfer Successful");
        this.getAccountByAccId();
        console.log("tranfer ok");
        this.tranferForm.reset();
      },
      error: (err: any) => {
        alert("Giao dịch không thành công!!!");
        console.log(err);
        this.tranferForm.reset();  // Reset the form here as well
      }
    });
  }
  

  getUserByUserId() {
    this.userService.getUserById(this.userId).subscribe({
      next: (respone: any) => {
        console.log(respone.fullname);
        this.fullname = respone.fullname;
      },
      error: (err: any) => {
        console.log("error: ", err);
      }
    });
  }



  getAccountByAccId() {
    this.accountService.getAccountByAccId(this.accountId).subscribe({
      next: (response: any) => {
        this.accounts = response;
        this.balance = this.accounts.balance;
        this.formattedBalance! = this.decimalPipe.transform(this.balance, '1.0-0')!;
      },
      error: (err: any) => {
        console.log("error: ", err);
      }
    });
  }
}
