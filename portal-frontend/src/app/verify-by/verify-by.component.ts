import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from "@angular/common";
import {VerificationService} from "../_services/verification.service";
import {AuthService} from "../_services/auth.service";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-verify-by',
  templateUrl: './verify-by.component.html',
  styleUrls: ['./verify-by.component.css']
})
export class VerifyByComponent implements OnInit {

  mentorName;
  success = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private verificationService: VerificationService,
    private authService: AuthService,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe((params: Params) => {
      // if JWT is supplied save it and delete from the history.
      const hash = params['hash'];
      if (hash) {

        if(!this.authService.isLoggedIn()){
          console.log("Not logged in, redirecting to apply page");
          this.location.replaceState('hash');
          this.router.navigate(['apply'], { queryParams: { hash: hash } });
          return;
        }

        this.verificationService.addMentor(hash).subscribe(
          (data: any) => {
            if(data['success']){
              this.mentorName = data['name'];
              this.snackbar.open("You just got a new Mentor!", null, {
                duration: 1500
              });
              this.location.replaceState('hash');
            } else {
              this.success = false;
            }
          }
        );
      }
    });

  }

  proceed(){
    this.router.navigate(['menu']);
  }

}
