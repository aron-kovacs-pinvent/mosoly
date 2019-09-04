import { Component, OnInit } from '@angular/core';
import {VerificationService} from "../_services/verification.service";
import {MatSnackBar} from "@angular/material";
import {Router} from "@angular/router";
import {AuthService} from "../_services/auth.service";

@Component({
  selector: 'app-verification-menu',
  templateUrl: './verification-menu.component.html',
  styleUrls: ['./verification-menu.component.css']
})
export class VerificationMenuComponent implements OnInit {

  needsValidation;
  name;

  constructor(
    private verificationService: VerificationService,
    private snackbar: MatSnackBar,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.authService.me().subscribe((data: any) => {
      this.authService.set(data);
      for (const m of data.mentorees) {
        if (!m.validated) {
          this.needsValidation = m;
        }
      }
    });
  }

  validate(){
    this.verificationService.validate(this.needsValidation.account).subscribe((data: any) => {
      if(data['success']){
        this.verificationService.nickName(this.name, this.needsValidation.account).subscribe((data2: any) => {
          this.snackbar.open("Validation successful", null, {
            duration: 1500
          });
          this.router.navigate(['menu']);
        });

      }
    })
  }
}
