import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from './service/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const expectedRoles = route.data['expectedRoles'] as string[];
  const userRoles = authService.getRole(); // assuming userRoles is an array

  if (userRoles && expectedRoles.some(role => userRoles.includes(role))) {
    return true;
  } else {
    // Navigate to a forbidden page
    router.navigate(['/forbidden']);
    return false;
  }
};
