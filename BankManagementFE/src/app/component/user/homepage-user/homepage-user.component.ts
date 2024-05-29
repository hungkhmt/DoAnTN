import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, ChartModule } from 'angular-highcharts';
import * as Highcharts from 'highcharts';
import { UserService } from '../../../service/user.service';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-homepage-user',
  standalone: true,
  imports: [CommonModule, ChartModule, RouterModule],
  templateUrl: './homepage-user.component.html',
  styleUrls: ['./homepage-user.component.css']
})
export class HomepageUserComponent {

  constructor(private userService: UserService, private accountService: AccountService, private authService: AuthService){}

  fullname: String | undefined;
  type: String | undefined;
  spendLimit: String | undefined;

  chartData = [
    {
      name: 'Test',
      data: [1, 2, 7, 4, 5]
    }
  ];

  lineChart = new Chart({
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
      categories: ['user1', 'user2', 'user3', 'user4', 'user5']
    },
    series: this.chartData as any
  });
}
