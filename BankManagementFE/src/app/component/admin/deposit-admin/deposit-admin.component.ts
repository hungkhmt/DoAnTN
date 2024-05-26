import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/select';

@Component({
  selector: 'app-deposit-admin',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule],
  templateUrl: './deposit-admin.component.html',
  styleUrl: './deposit-admin.component.css'
})
export class DepositAdminComponent {

}
