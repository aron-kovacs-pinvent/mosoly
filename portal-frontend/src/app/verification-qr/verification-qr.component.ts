import { Component, OnInit } from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {VerificationService} from "../_services/verification.service";
import {Router} from "@angular/router";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material";

@Component({
  selector: 'app-verification-qr',
  templateUrl: './verification-qr.component.html',
  styleUrls: ['./verification-qr.component.css']
})
export class VerificationQrComponent implements OnInit {

  qrData;
  needsValidation: string;
  needsValidationName: string;

  constructor(
    private authService: AuthService,
    private verificationService: VerificationService,
    private router: Router,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit() {
    this.qrData = "https://portal.mosoly.live/verify-by?hash=" + this.authService.user().inviteUrlHash;
  }

  refresh(){
    this.authService.me().subscribe((data: any) => {
      this.authService.set(data);
      for(const m of data.mentorees){
        if(!m.validated){
          this.needsValidation = m.account;
          this.needsValidationName = m.customNameForMentor;
        }
      }
      if(!this.needsValidation){
        this.snackbar.open("Nothing happened recently", null, {
          duration: 1500
        })
      } else {
        this.router.navigate(['verification']);
      }
    })
  }



}
