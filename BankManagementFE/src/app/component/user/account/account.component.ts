import { Component, AfterViewInit, ViewEncapsulation } from '@angular/core';
import { Account } from '../../../model/account';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AccountComponent implements AfterViewInit{
  accounts: Account[] = [];
  userId = this.authService.getUserId();

  constructor(private accountService: AccountService, private authService: AuthService) {}

  ngOnInit(): void {
    this.accountService.getAllAccountByUserId(this.userId).subscribe((data: any[]) => {
      this.accounts = data;
    });
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
    }
  }

  prevSlide() {
    const items = document.querySelectorAll('.item') as NodeListOf<HTMLElement>;
    if (items.length > 0) {
      document.querySelector('.slide')?.prepend(items[items.length - 1]);
    }
  }
}
