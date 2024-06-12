import { Component, INJECTOR, Inject } from '@angular/core';
import {
  MatDialogRef,
  MatDialogActions,
  MatDialogClose,
  MatDialogTitle,
  MatDialogContent,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button'
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatRadioModule} from '@angular/material/radio';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import { UserService } from '../../../service/user.service';
import { AccountService } from '../../../service/account.service';

@Component({
  selector: 'app-confirm-enable',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent,
    MatInputModule, MatFormFieldModule, MatRadioModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './confirm-enable.component.html',
  styleUrl: './confirm-enable.component.css'
})
export class ConfirmEnableComponent {
  accountForm = new FormGroup({
    accountId: new FormControl('')
  });
  constructor(private accountService: AccountService, private dilogRef: MatDialogRef<ConfirmEnableComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    this.accountForm.patchValue(this.data)
  }

  onFormSubmit() {
    this.accountService.enableAccount(this.accountForm.value.accountId).subscribe({
      next: (val: any) => {
        alert("Enable Account Successful")
        this.dilogRef.close(true);
      }, error: (err: any) => {
        console.log(err)
      }
    })
  }
}
