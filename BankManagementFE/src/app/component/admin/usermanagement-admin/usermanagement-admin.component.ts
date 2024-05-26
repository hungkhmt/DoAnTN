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
import { Transaction } from '../../../model/transaction';
import { Role, User } from '../../../model/user';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-usermanagement-admin',
  standalone: true,
  imports: [MatToolbarModule, MatIconModule, MatButtonModule, MatTableModule, MatPaginatorModule, MatSortModule, RouterModule],
  templateUrl: './usermanagement-admin.component.html',
  styleUrl: './usermanagement-admin.component.css'
})
export class UsermanagementAdminComponent {
  displayedColumns: string[] = ['fullname', 'phoneNumber', 'username', 'dateOfBirth', 'email', 'address', 'created_at', 'roles', 'action'];
  dataSource = new MatTableDataSource<User>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  roles: Role | undefined;

  constructor(public _dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer, private userService: UserService) {}

  ngOnInit() {
    this.getAllUser();
  }

  getAllUser() {
    this.userService.getAllUserWithRole().subscribe({
      next: (res) => {
        // Chuyển đổi dữ liệu trước khi gán vào dataSource
        const usersWithFormattedRoles = res.map((user: User) => ({
          ...user,
          phoneNumber: this.transform(user.phoneNumber),
          roles: user.roles.map(role => `${role.roleName}`).join(', ')
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

  getRolesDisplay(roles1: Role[]): string {
    if (Array.isArray(roles1)) {
      // Nếu roles là một mảng, thực hiện map
        return roles1.map(role => role.roleName).join(', ');
      }

    return '';    
  }
  
  transform(phoneNumber: any): string {
    return `${phoneNumber.substr(0, 3)}***${phoneNumber.substr(-3)}`;
  }



  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }


}
