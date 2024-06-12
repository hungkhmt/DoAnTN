import { Component } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { Chart, ChartModule } from 'angular-highcharts';
import * as Highcharts from 'highcharts';
import { UserService } from '../../../service/user.service';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';
import { RouterModule, Router } from '@angular/router';
import { Transaction } from '../../../model/transaction';
import { TranferService } from '../../../service/tranfer.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { AddCardComponent } from '../add-card/add-card.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-homepage-user',
  standalone: true,
  imports: [CommonModule, ChartModule, RouterModule, MatDatepickerModule, MatNativeDateModule, ReactiveFormsModule, MatFormField, MatLabel],
  providers: [DecimalPipe],
  templateUrl: './homepage-user.component.html',
  styleUrls: ['./homepage-user.component.css']
})
export class HomepageUserComponent {
  constructor(private userService: UserService, private accountService: AccountService, public _dialog: MatDialog,
              private authService: AuthService, private transactionService: TranferService, private decimalPipe: DecimalPipe){}

  fullname: String | undefined;
  type: String | undefined;
  accId = sessionStorage.getItem('currentAccount');
  accIdStr: string | undefined;
  userId = this.authService.getUserId();
  listAcc: any[] | undefined;
  formattedBalance: any;

  month: number | undefined;
  chartData: any[] = [];

  lineChart!: Chart;

  transactions: Transaction[] = [];
  totalPage: number = 0;
  page: number = 0;
  limit: number = 4;

  ngOnInit() {
    this.setCurrentMonth();
    this.getTransactionApi(this.month!);
    this.setAccountId();
    this.accIdStr = this.formatNumber(this.accId);
    this.getUserByUserId();
    this.getAllAccount();
    this.loadTransactions(this.accId);
  }

  updateChartData(transactions1: Transaction[], transactions2: Transaction[]) {
    const datesSet = new Set<string>();

    transactions1.forEach(transaction => {
      datesSet.add(new Date(transaction.transactionDate).toISOString().split('T')[0]);
    });

    transactions2.forEach(transaction => {
      datesSet.add(new Date(transaction.transactionDate).toISOString().split('T')[0]);
    });

    const dates = Array.from(datesSet).sort();

    const aggregateData = (transactions: Transaction[], dates: string[]) => {
      const dataMap: { [key: string]: number } = {};

      transactions.forEach(transaction => {
        const dateStr = new Date(transaction.transactionDate).toISOString().split('T')[0];
        if (!dataMap[dateStr]) {
          dataMap[dateStr] = 0;
        }
        dataMap[dateStr] += transaction.amount;
      });

      return dates.map(date => dataMap[date] || 0);
    };

    const data1 = aggregateData(transactions1, dates);

    const data2 = aggregateData(transactions2, dates);

    this.chartData = [
      {
        name: 'Money received',
        data: data2,
        color: 'green'
      },
      {
        name: 'Money paid',
        data: data1,
        color: 'red'
      }
    ];

    this.initializeChart(dates);
  }

  initializeChart(dates: string[]) {
    this.lineChart = new Chart({
      chart: {
        type: 'line'
      },
      title: {
        text: 'Transaction'
      },
      credits: {
        enabled: false
      },
      accessibility: {
        enabled: false
      },
      xAxis: {
        categories: dates,
        title: {
          text: 'Date'
        },
        labels: {
          formatter: function() {
            const dateStr1 = String(this.value);
            return dateStr1.split('-')[2]; // Chỉ hiển thị ngày
          }
        }
      },
      yAxis: {
        title: {
          text: 'Amount'
        }
      },
      series: this.chartData as any
    });
  }

  getTransactionApi(month: number) {
    this.transactionService.getAllTransactionSourceId(this.accId, month).subscribe(transactions1 => {
      this.transactionService.getAllTransactionDestinationId(this.accId, month).subscribe(transactions2 => {
        this.updateChartData(transactions1, transactions2);
        console.log(this.month);
        // this.initializeChart();
      });
    });
  }

  dateControl = new FormControl(new Date());

  setCurrentMonth() {
    const currentDate = new Date();
    this.month = currentDate.getMonth() + 1; // Lưu trữ tháng dưới dạng số (1-12)
    this.updateMonthDisplay(currentDate);
    
  }

  updateMonthDisplay(date: Date) {
    const month = date.toLocaleString('default', { month: 'long' });
    const year = date.getFullYear();
    const formattedDate = `${month} ${year}`;
    const datePickerElement = document.querySelector('.date-picker');
    if (datePickerElement) {
      datePickerElement.textContent = formattedDate;
    }
  }

  onDateChange(event: any) {
    const selectedDate = new Date(event.value);
    this.month = selectedDate.getMonth() + 1;
    this.updateMonthDisplay(selectedDate);
  }

  setPreviousMonth() {
    const currentDate = this.dateControl.value as Date;
    const previousMonth = new Date(currentDate.setMonth(currentDate.getMonth() - 1));
    this.dateControl.setValue(previousMonth);
    this.month = previousMonth.getMonth() + 1;
    this.updateMonthDisplay(previousMonth);
    this.getTransactionApi(this.month);
  }

  setNextMonth() {
    const nowDate = new Date();
    const nowMonth = nowDate.getMonth() + 1;
    const currentDate = this.dateControl.value as Date;
    const nextMonth = new Date(currentDate.setMonth(currentDate.getMonth() + 1));
    
    if(nextMonth.getMonth() < nowMonth) {
      this.dateControl.setValue(nextMonth);
      this.month = nextMonth.getMonth() + 1;
      this.updateMonthDisplay(nextMonth);
      this.getTransactionApi(this.month);
    } else {
      alert("Invalid month!!!");
    }
    
  }

  setAccountId() {
    this.accountService.getAllAccountByUserId(this.authService.getUserId()).subscribe(
      respone => {
        if(respone.length > 0) {
          sessionStorage.setItem('currentAccount', respone[0].accountId);
        }
        // this.accId = respone[0].accountId;
      }
    )
  }

  formatNumber(value: any): any {
    if(value){
      return value.replace(/(\d{4})(?=\d)/g, '$1 ');
    }
    return null;
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

  getAllAccount() {
    this.accountService.getAllAccountByUserId(this.userId).subscribe((data: any[]) => {
      this.listAcc = data;
    });
  }

  currentSlide = 0;

  get transformStyle() {
    return `translateX(-${this.currentSlide * 100}%)`;
  }

  prevSlide() {
    this.currentSlide = (this.currentSlide > 0) ? this.currentSlide - 1 : this.listAcc!?.length - 1;
    sessionStorage.setItem('currentAccount', this.listAcc![this.currentSlide].accountId);
    this.accId = sessionStorage.getItem('currentAccount');
    this.accIdStr = this.formatNumber(this.accId);
    this.getTransactionApi(this.month!);
    this.loadTransactions(this.accId);
  }

  nextSlide() {
    this.currentSlide = (this.currentSlide + 1) % this.listAcc!?.length;
    sessionStorage.setItem('currentAccount', this.listAcc![this.currentSlide].accountId);
    this.accId = sessionStorage.getItem('currentAccount');
    this.accIdStr = this.formatNumber(this.accId);
    this.getTransactionApi(this.month!);
    this.loadTransactions(this.accId);
  }

  loadTransactions(accountId: any) {
    this.transactionService.getTransactionsByAccountId(accountId, this.page, this.limit).subscribe(
      data => {
        const transactionFormattedAmount = data.transactionList.map((transaction: Transaction) => ({
          ...transaction,
          amount: this.decimalPipe.transform(transaction.amount, '1.0-0')!
        }));

        this.transactions = transactionFormattedAmount;
        this.totalPage = data.totalPage;
      },
      error => {
        console.error('Error fetching transactions', error);
      }
    );
  }

  onPageChange(newPage: number): void {
    this.page = newPage;
    this.loadTransactions(this.accId);
  }

  getAmountPrefix(transaction: Transaction): string {
    if (transaction.sourceAccountId == this.accId) {
      return '-';
    } else if (transaction.destinationAccountId == this.accId) {
      return '+';
    }
    return '';
  }

  openAddCardForm() {
    const dialogRef = this._dialog.open(AddCardComponent)
    dialogRef.afterClosed().subscribe({
      next: (res: any) => {
        location.reload();
      }, error: (err) => {
        alert('fail')
      }
    })
  }
}
