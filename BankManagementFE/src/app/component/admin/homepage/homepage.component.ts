import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, ChartModule } from 'angular-highcharts';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [ChartModule, CommonModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.css'
})
export class HomepageComponent {

  chartData = [
    {
      name: 'Test',
      data: [1,2,7,4,5]
    }
  ]

  lineChart = new Chart({
    chart: {
      type: 'column'
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
  })

  

}
