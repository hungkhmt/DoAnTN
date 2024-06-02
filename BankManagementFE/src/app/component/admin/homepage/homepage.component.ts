import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, ChartModule } from 'angular-highcharts';
import { UserService } from '../../../service/user.service';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';
import { TranferService } from '../../../service/tranfer.service';
import { Transaction } from '../../../model/transaction';
import { FormControl } from '@angular/forms';
import { Account } from '../../../model/account';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [CommonModule, ChartModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {
  constructor(private userService: UserService, private accountService: AccountService,
              private authService: AuthService, private transactionService: TranferService){}

  fullname: String | undefined;
  type: String | undefined;
  accId = sessionStorage.getItem('currentAccount');
  accIdStr: string | undefined;
  userId = this.authService.getUserId();
  listAcc: any[] | undefined;

  monthTrans: number | undefined;
  monthAccs: number | undefined;
  chartTransactionData: any[] = [];
  chartAccountData: any[] = [];

  lineChart!: Chart;

  lineChart2!: Chart;

  ngOnInit() {
    this.setCurrentMonthTrans();
    this.setCurrentMonthAccs();
    this.getTransactionApi(this.monthTrans!);
    this.getAccountApi(this.monthAccs!);
  }

  updateChartTransactionData(transactions: Transaction[]) {
    const datesSet = new Set<string>();

    transactions.forEach(transaction => {
      datesSet.add(new Date(transaction.transactionDate).toISOString().split('T')[0]);
    });

    const dates = Array.from(datesSet).sort();

    const aggregateData = (transactions: Transaction[], dates: string[]) => {
      const dataMap: { [key: string]: number } = {};

      transactions.forEach(transaction => {
        const dateStr1 = new Date(transaction.transactionDate).toISOString().split('T')[0];
        if (!dataMap[dateStr1]) {
          dataMap[dateStr1] = 0;
        }
        dataMap[dateStr1] += 1;
      });

      return dates.map(date => dataMap[date] || 0);
    };

    const data1 = aggregateData(transactions, dates);

    this.chartTransactionData = [
      {
        name: 'Transaction',
        data: data1,
        color: 'green'
      }
    ];

    this.initializeChart(dates);
  }

  updateChartAccountData(accounts: Account[]) {
    const datesSet = new Set<string>();

    accounts.forEach(account => {
      datesSet.add(new Date(account.createdAt).toISOString().split('T')[0]);
    });

    const dates = Array.from(datesSet).sort();

    const aggregateData = (accounts: Account[], dates: string[]) => {
      const dataMap: { [key: string]: number } = {};

      accounts.forEach(account => {
        const dateStre2 = new Date(account.createdAt).toISOString().split('T')[0];
        if (!dataMap[dateStre2]) {
          dataMap[dateStre2] = 0;
        }
        dataMap[dateStre2] += 1;
      });

      return dates.map(date => dataMap[date] || 0);
    };

    const data2 = aggregateData(accounts, dates);

    this.chartAccountData = [
      {
        name: 'Account',
        data: data2,
        color: 'blue'
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
      series: this.chartTransactionData as any
    });

    this.lineChart2 = new Chart({
      chart: {
        type: 'line'
      },
      title: {
        text: 'Account'
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
            const dateStre2 = String(this.value);
            return dateStre2.split('-')[2]; // Chỉ hiển thị ngày
          }
        }
      },
      yAxis: {
        title: {
          text: 'Amount'
        }
      },
      series: this.chartAccountData as any
    });
  }

  getTransactionApi(month: number) {
    this.transactionService.getAllTransactionByMonth(month).subscribe(transactions => {
      this.updateChartTransactionData(transactions);
    });
  }

  getAccountApi(month: number) {
    this.accountService.getAllAccountByMonth(month).subscribe(accounts => {
      this.updateChartAccountData(accounts);
    });
  }

  dateControlTrans = new FormControl(new Date());
  dateControlAccs = new FormControl(new Date());

  setCurrentMonthTrans() {
    const currentDate = new Date();
    this.monthTrans = currentDate.getMonth() + 1; // Lưu trữ tháng dưới dạng số (1-12)
    this.updateMonthDisplayTrans(currentDate);
    
  }

  setCurrentMonthAccs() {
    const currentDate = new Date();
    this.monthAccs= currentDate.getMonth() + 1; // Lưu trữ tháng dưới dạng số (1-12)
    this.updateMonthDisplayAccs(currentDate);
    
  }

  updateMonthDisplayTrans(date: Date) {
    const month = date.toLocaleString('default', { month: 'long' });
    const year = date.getFullYear();
    const formattedDate = `${month} ${year}`;
    const datePickerElement1 = document.querySelector('.date-picker-trans');
    if (datePickerElement1) {
      datePickerElement1.textContent = formattedDate;
    }
  }

  updateMonthDisplayAccs(date: Date) {
    const month = date.toLocaleString('default', { month: 'long' });
    const year = date.getFullYear();
    const formattedDate = `${month} ${year}`;
    const datePickerElement2 = document.querySelector('.date-picker-accs');
    if (datePickerElement2) {
      datePickerElement2.textContent = formattedDate;
    }
  }

  onDateChangeTrans(event: any) {
    const selectedDate = new Date(event.value);
    this.monthTrans = selectedDate.getMonth() + 1;
    this.updateMonthDisplayTrans(selectedDate);
  }

  onDateChangeAccs(event: any) {
    const selectedDate = new Date(event.value);
    this.monthAccs = selectedDate.getMonth() + 1;
    this.updateMonthDisplayAccs(selectedDate);
  }

  setPreviousMonthTrans() {
    const currentDate = this.dateControlTrans.value as Date;
    const previousMonth = new Date(currentDate.setMonth(currentDate.getMonth() - 1));
    this.dateControlTrans.setValue(previousMonth);
    this.monthTrans = previousMonth.getMonth() + 1;
    this.updateMonthDisplayTrans(previousMonth);
    this.getTransactionApi(this.monthTrans);
  }

  setNextMonthTrans() {
    const nowDate = new Date();
    const nowMonth = nowDate.getMonth() + 1;
    const currentDate = this.dateControlTrans.value as Date;
    const nextMonth = new Date(currentDate.setMonth(currentDate.getMonth() + 1));
    
    if(nextMonth.getMonth() < nowMonth) {
      this.dateControlTrans.setValue(nextMonth);
      this.monthTrans = nextMonth.getMonth() + 1;
      this.updateMonthDisplayTrans(nextMonth);
      this.getTransactionApi(this.monthTrans);
    } else {
      alert("Invalid month!!!");
    }
    
  }

  setPreviousMonthAcc() {
    const currentDate = this.dateControlAccs.value as Date;
    const previousMonth = new Date(currentDate.setMonth(currentDate.getMonth() - 1));
    this.dateControlAccs.setValue(previousMonth);
    this.monthAccs = previousMonth.getMonth() + 1;
    this.updateMonthDisplayAccs(previousMonth);
    this.getAccountApi(this.monthAccs);
  }

  setNextMonthAcc() {
    const nowDate = new Date();
    const nowMonth = nowDate.getMonth() + 1;
    const currentDate = this.dateControlAccs.value as Date;
    const nextMonth = new Date(currentDate.setMonth(currentDate.getMonth() + 1));
    
    if(nextMonth.getMonth() < nowMonth) {
      this.dateControlAccs.setValue(nextMonth);
      this.monthAccs = nextMonth.getMonth() + 1;
      this.updateMonthDisplayAccs(nextMonth);
      this.getAccountApi(this.monthAccs);
    } else {
      alert("Invalid month!!!");
    }
    
  }
}
