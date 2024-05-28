import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { SidebarAdminComponent } from './component/admin/sidebar-admin/sidebar-admin.component';
import { DashboardAdminComponent } from './component/admin/dashboard-admin/dashboard-admin.component';
import { DepositAdminComponent } from './component/admin/deposit-admin/deposit-admin.component';
import { WithdrawAdminComponent } from './component/admin/withdraw-admin/withdraw-admin.component';
import { UsermanagementAdminComponent } from './component/admin/usermanagement-admin/usermanagement-admin.component';
import { TransactionAdminComponent } from './component/admin/transaction-admin/transaction-admin.component';
import { ProfileComponent } from './component/profile/profile.component';
import { HomepageComponent } from './component/admin/homepage/homepage.component';
import { HomepageUserComponent } from './component/user/homepage-user/homepage-user.component';
import { authGuard } from './auth.guard';
import { ForbiddenComponent } from './component/forbidden/forbidden.component';
import { TransactionComponent } from './component/user/transaction/transaction.component';
import { AccountComponent } from './component/user/account/account.component';
import { SidebarUserComponent } from './component/user/sidebar-user/sidebar-user.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { AccountmanagementAdminComponent } from './component/admin/accountmanagement-admin/accountmanagement-admin.component';

export const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        title: 'Login',
    },
    {
      path: 'admin',
      component: SidebarAdminComponent,
      children: [
        { path: 'homepage', component: HomepageComponent },
        {
          path: 'dashboard',
          component: DashboardAdminComponent,
        },
        {
          path: 'deposit',
          component: DepositAdminComponent,
        },
        {
          path: 'withdraw',
          component: WithdrawAdminComponent
        },
        {
          path: 'user',
          component: UsermanagementAdminComponent
        },
        {
          path: 'account',
          component: AccountmanagementAdminComponent
        },
        {
          path: 'transaction',
          component: TransactionAdminComponent,
        },
        {
          path: 'profile',
          component: ProfileComponent,
        },
      ],
      title: 'AdminDashboard',
      // canActivate: [authGuard],
      // data: { expectedRole: 'ADMIN' }
    },
    {
      path: 'user',
      component: SidebarUserComponent,
      children: [
        { path: 'homepage', component: HomepageUserComponent },
        {
          path: 'transaction',
          component: TransactionComponent,
        },
        {
          path: 'account',
          component: AccountComponent,
        },
        {
          path: 'profile',
          component: ProfileComponent,
        }
      ],
      title: 'E-Banking'
    },
    {
      path: 'forbidden',
      component: ForbiddenComponent,
      title: "Forbidden"
    },
    {
      path: 'registration',
      component: RegistrationComponent,
      title: "Welcome"
    } 
];
