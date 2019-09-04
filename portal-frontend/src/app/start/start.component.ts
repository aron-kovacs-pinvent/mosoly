import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Location} from '@angular/common';
import {AuthService} from "../_services/auth.service";

@Component({
    templateUrl: 'start.component.html',
    styleUrls: ['start.component.css']
})
export class StartComponent implements OnInit {

    constructor(private location: Location,
                private router: Router,
                private authService: AuthService,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        if(!this.authService.isLoggedIn()){
          this.router.navigate(['login']);
        }
        else {
          this.router.navigate(['menu'])
        }
    }
}
