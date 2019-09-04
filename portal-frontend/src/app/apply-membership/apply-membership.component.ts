import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {VerificationService} from "../_services/verification.service";
import {Location} from "@angular/common";
import {AuthService} from "../_services/auth.service";
import {MatSnackBar} from "@angular/material";
import {RegistrationModel} from "../_models/registration.model";

@Component({
  selector: 'app-apply-membership',
  templateUrl: './apply-membership.component.html',
  styleUrls: ['./apply-membership.component.css']
})
export class ApplyMembershipComponent implements OnInit {

  model = new RegistrationModel();
  mentorName;

  constructor(
    private route: ActivatedRoute,
    private verificationService: VerificationService,
    private location: Location,
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe((params: Params) => {
      // if JWT is supplied save it and delete from the history.

      if (params['hash']) {
        this.verificationService.getMentorName(params['hash']).subscribe(
          (data: any) => {
            if(data['success']){
              this.mentorName = data['name'];
              this.model['mentor1Hash'] = params['hash'];
              this.snackbar.open("Congrats, you just got a new mentor!", null, {
                duration: 1500
              });
              this.location.replaceState('apply');
            }
          }
        );
      }
    });
  }

  register(){
    this.authService.register(this.model).subscribe((data: any) => {
      this.snackbar.open("Success! Now please log in!", null, {
        duration: 3000
      });
      this.router.navigate(['login'])
    })
  }

}
