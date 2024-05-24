import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/select';
import {MatCardModule} from '@angular/material/card';

@Component({
  selector: 'app-withdraw-admin',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule, MatCardModule],
  templateUrl: './withdraw-admin.component.html',
  styleUrl: './withdraw-admin.component.css'
})
export class WithdrawAdminComponent {

}
