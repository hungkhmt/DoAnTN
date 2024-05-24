import { Component, ViewChild } from '@angular/core';
import { MatToolbarModule} from '@angular/material/toolbar'
import { MatIconModule } from '@angular/material/icon'
import { MatButtonModule } from '@angular/material/button'
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatSort, Sort, MatSortModule} from '@angular/material/sort';
import { RouterModule, Router } from '@angular/router';
import {
  MatDialog,
  MatDialogRef,
  MatDialogActions,
  MatDialogClose,
  MatDialogTitle,
  MatDialogContent
} from '@angular/material/dialog';
import {LiveAnnouncer} from '@angular/cdk/a11y';
import { User } from '../../../model/user';
import { Transaction } from '../../../model/transaction';

@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [MatToolbarModule, MatIconModule, MatButtonModule, MatTableModule, MatPaginatorModule, MatSortModule, RouterModule],
  templateUrl: './dashboard-admin.component.html',
  styleUrl: './dashboard-admin.component.css'
})
export class DashboardAdminComponent {
  displayedColumns: string[] = ['Id', 'sourceAccountId', 'destinationAccountId', 'amount', 'transactionType', 'transactionDate', 'description'];
  dataSource = new MatTableDataSource<Transaction>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  constructor(public _dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer) {}

  ngOnInit() {
    this.dataSource = new MatTableDataSource(this.transactionList);
  }

  transactionList: Transaction[] = [
    {
      id: 0,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    },
    {
      id: 1,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    },
    {
      id: 2,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    },
    {
      id: 3,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    },
    {
      id: 4,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    },
    {
      id: 5,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    },
    {
      id: 6,
      sourceAccountId: "12345678",
      destinationAccountId: "12345679",
      amount: 120000,
      transactionType: "TRANFER",
      transactionDate: "12/05/2024",
      description: "NGUYEN THE HUNG chuyen khoan"
    }
  ]


  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }


}
