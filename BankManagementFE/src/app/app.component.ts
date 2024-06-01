import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { SidebarAdminComponent } from './component/admin/sidebar-admin/sidebar-admin.component';
import { LoginComponent } from './component/login/login.component';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SidebarAdminComponent, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'BankManagementFE';
  constructor(private router: Router, private authService: AuthService) {}
  ngOnInit() {
    const token = this.authService.getToken();
    if (!token) {
      this.router.navigate(['/registration']);
    }
  }
}
