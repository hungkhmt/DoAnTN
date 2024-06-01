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
import { AccountService } from '../../../service/account.service';
import { Account } from '../../../model/account';

@Component({
  selector: 'app-accountmanagement-admin',
  standalone: true,
  imports: [MatToolbarModule, MatIconModule, MatButtonModule, MatTableModule, MatPaginatorModule, MatSortModule, RouterModule],
  templateUrl: './accountmanagement-admin.component.html',
  styleUrl: './accountmanagement-admin.component.css'
})
export class AccountmanagementAdminComponent {
  displayedColumns: string[] = ['customerId', 'accountId', 'accountType', 'balance', 'createdAt', 'accountStatus', 'action'];
  dataSource = new MatTableDataSource<Account>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  constructor(public _dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer, private accountService: AccountService) {}

  ngOnInit() {
    this.getAllUser();
  }

  // openAddUserForm() {
  //   console.log("ready");
  //   const dialogRef = this._dialog.open(AddEditUserComponent);
  //   dialogRef.afterClosed().subscribe({
  //     next: (res: any) => {
  //       this.getAllUser();
  //     }, error: (err) => {
  //       alert('fail')
  //     }
  //   })
  // }

  getAllUser() {
    this.accountService.getAllAcount().subscribe({
      next: (res) => {
        const usersWithFormattedRoles = res.map((account: Account) => ({
          ...account,
          createdAt: this.formatDate(account.createdAt)
        }));
        this.dataSource = new MatTableDataSource(usersWithFormattedRoles);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  // deleteUser(id: number) {
  //   this.userService.disableUser(id).subscribe({
  //     next: (res) => {
  //       alert("Disable User id: " + id);
  //       this.getAllUser();
  //     }, error: (err) => {
  //       alert('Disable Fail')
  //     }
  //   })
  // }

  formatDate(date: string): string {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();
  
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
  
    return [day, month, year].join('/');
  }


  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }


}
