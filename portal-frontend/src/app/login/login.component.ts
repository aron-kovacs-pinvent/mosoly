import {Component, OnInit} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showPasswd = false;

  loginInput: string;
  attr: string;
  password: string;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    if(this.authService.isLoggedIn()){
      this.router.navigate(['menu']);
    }
  }

  loginGiven() {
    this.authService.check(this.loginInput.toLowerCase()).subscribe(
      (data: any) => {
        if (data['success']) {
          this.attr = data['attribute'];
          this.showPasswd = true;
        }
      }
    );
  }

  login(){
    this.authService.login(this.attr, this.loginInput.toLowerCase(), this.password).subscribe(
      (data: any)  => {
        this.authService.set(data);
        this.router.navigate(['menu']);
      }
    )
  }

}
