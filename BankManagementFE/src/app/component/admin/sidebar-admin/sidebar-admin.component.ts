import { Component, OnInit, Inject, Renderer2, HostListener } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-sidebar-admin',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './sidebar-admin.component.html',
  styleUrls: ['./sidebar-admin.component.css']
})
export class SidebarAdminComponent implements OnInit {
  searchIcon: string = 'bx bx-search';

  constructor(private renderer: Renderer2, @Inject(DOCUMENT) private document: Document, private authService: AuthService,
              private router: Router) {}

  ngOnInit() {
    this.setupMenuClickHandlers();
    this.handleWindowResize();
  }

  setupMenuClickHandlers() {
    const allSideMenu = this.document.querySelectorAll('#sidebar .side-menu.top li a');
    allSideMenu.forEach(item => {
      const li = item.parentElement;
      if (li) {
        this.renderer.listen(item, 'click', () => {
          allSideMenu.forEach(i => {
            const parentLi = i.parentElement;
            if (parentLi) {
              this.renderer.removeClass(parentLi, 'active');
            }
          });
          this.renderer.addClass(li, 'active');
        });
      }
    });
  }

  toggleSidebar() {
    const sidebar = this.document.getElementById('sidebar');
    if (sidebar) {
      if (sidebar.classList.contains('hide')) {
        this.renderer.removeClass(sidebar, 'hide');
      } else {
        this.renderer.addClass(sidebar, 'hide');
      }
    }
  }

  toggleSearch(event: Event) {
    if (window.innerWidth < 576) {
      event.preventDefault();
      const searchForm = this.document.querySelector('#content nav form');
      if (searchForm) {
        if (searchForm.classList.contains('show')) {
          this.renderer.removeClass(searchForm, 'show');
          this.searchIcon = 'bx bx-search';
        } else {
          this.renderer.addClass(searchForm, 'show');
          this.searchIcon = 'bx bx-x';
        }
      }
    }
  }

  @HostListener('window:resize')
  handleWindowResize() {
    const sidebar = this.document.getElementById('sidebar');
    const searchButtonIcon = this.document.querySelector('#content nav form .form-input button .bx');
    const searchForm = this.document.querySelector('#content nav form');

    if (sidebar && searchButtonIcon && searchForm) {
      if (window.innerWidth < 768) {
        this.renderer.addClass(sidebar, 'hide');
      } else {
        this.renderer.removeClass(sidebar, 'hide');
      }

      if (window.innerWidth < 576) {
        this.renderer.removeClass(searchButtonIcon, 'bx-x');
        this.renderer.addClass(searchButtonIcon, 'bx-search');
        this.renderer.removeClass(searchForm, 'show');
      }
    }
  }

  logout() {
    let token = sessionStorage.getItem('authToken');
    this.authService.logout(token!).subscribe(
      respone => {
        token = sessionStorage.getItem('authToken');
        if(respone && token == null) {
          
          this.router.navigate(['/registration']);
        }
      }
    )
  }
}
