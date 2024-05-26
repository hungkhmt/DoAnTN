import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { MatOption } from '@angular/material/select';
import {MatCardModule} from '@angular/material/card';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TranferService } from '../../../service/tranfer.service';


@Component({
  selector: 'app-transaction-admin',
  standalone: true,
  imports: [MatFormFieldModule, MatLabel, MatSelect, MatOption, MatInputModule, MatCardModule, ReactiveFormsModule],
  templateUrl: './transaction-admin.component.html',
  styleUrl: './transaction-admin.component.css'
})
export class TransactionAdminComponent {
  tranferForm = new FormGroup({
    sourceAccountId: new FormControl(''),
    destinationAccountId: new FormControl(''),
    amount: new FormControl(''),
    description: new FormControl('')
  })

  constructor(private tranferService: TranferService) {}

  onFormSubmit() {
      this.tranferService.tranferMoney(this.tranferForm.value).subscribe({
        next: (val: any) => {
          alert("Tranfer Successful")
        }, error: (err: any) => {
          console.log(err)
        }
      })
    
  }
}
