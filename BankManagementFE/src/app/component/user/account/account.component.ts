import { Component, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { Account } from '../../../model/account';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';
import { UserService } from '../../../service/user.service';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [CommonModule, RouterModule],
  providers: [DecimalPipe],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AccountComponent implements AfterViewInit{
  accounts: Account[] = [];
  userId = this.authService.getUserId();
  fullname: string | undefined;
  formattedBalance: string | undefined;

  constructor(private accountService: AccountService, private authService: AuthService, private userService: UserService) {}

  ngOnInit(): void {
    this.accountService.getAllAccountByUserId(this.userId).subscribe((data: any[]) => {
      this.accounts = data;
      console.log("lor", this.accounts);
    });
    this.getUserByUserId();
  }
  
  ngAfterViewInit() {
    const nextButton = document.querySelector('.next') as HTMLElement;
    const prevButton = document.querySelector('.prev') as HTMLElement;

    if (nextButton && prevButton) {
      nextButton.addEventListener('click', this.nextSlide);
      prevButton.addEventListener('click', this.prevSlide);
    }
  }

  nextSlide() {
    const items = document.querySelectorAll('.item') as NodeListOf<HTMLElement>;
    if (items.length > 0) {
      document.querySelector('.slide')?.appendChild(items[0]);
      const temp = document.getElementById('accountId');
      console.log(temp?.innerHTML);
      sessionStorage.setItem('currentAccount', temp!?.innerHTML);
    }
  }

  prevSlide() {
    const items = document.querySelectorAll('.item') as NodeListOf<HTMLElement>;
    if (items.length > 0) {
      document.querySelector('.slide')?.prepend(items[items.length - 1]);
      const temp = document.getElementById('accountId');
      console.log(temp?.innerHTML);
      sessionStorage.setItem('currentAccount', temp!?.innerHTML);
    }
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
}
