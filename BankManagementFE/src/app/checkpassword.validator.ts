import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';

export const PasswordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const formGroup = control as FormGroup;
  const newPassword = formGroup.controls['newPassword'].value;
  const confirmNewPassword = formGroup.controls['confirmNewPassword'].value;

  return newPassword === confirmNewPassword ? null : { mustMatch: true };
};
