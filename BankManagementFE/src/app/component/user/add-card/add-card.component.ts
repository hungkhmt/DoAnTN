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
import { AuthService } from '../../../service/auth.service';
import { AccountService } from '../../../service/account.service';

@Component({
  selector: 'app-add-card',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent,
    MatInputModule, MatFormFieldModule, MatRadioModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './add-card.component.html',
  styleUrl: './add-card.component.css'
})
export class AddCardComponent {
  cardForm = new FormGroup({
    customerId: new FormControl(''),
    accountType: new FormControl('')
  });

  constructor(private accountService: AccountService, private dilogRef: MatDialogRef<AddCardComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any, private authService: AuthService) {}

  onFormSubmit() {
    this.cardForm.patchValue({ customerId: this.authService.getUserId() });
    this.accountService.createAcc(this.cardForm.value).subscribe({
      next: (val: any) => {
        alert("Admin will ACTIVE your account. Please wait!!!");
        this.dilogRef.close(true);
      }, error: (err: any) => {
        console.log(err)
      }
    });
  }
}
