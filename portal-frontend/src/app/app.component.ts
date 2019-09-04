import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "./_services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  loggedIn(){
    return this.authService.isLoggedIn();
  }

  logout(){
    localStorage.removeItem("user");
    this.router.navigate(['login']);
  }
}
