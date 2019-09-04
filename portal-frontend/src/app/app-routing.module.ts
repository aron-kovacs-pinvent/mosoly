import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AdminMenuComponent} from './admin-menu/admin-menu.component';
import {ApplicantQrComponent} from './applicant-qr/applicant-qr.component';
import {ApplyMembershipComponent} from './apply-membership/apply-membership.component';
import {VerificationMenuComponent} from './verification-menu/verification-menu.component';
import {VerificationQrComponent} from './verification-qr/verification-qr.component';
import {StartComponent} from "./start/start.component";
import {VerifyByComponent} from "./verify-by/verify-by.component";
import {AuthGuard} from "./_guards/auth.guard";


const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'menu',
    component: AdminMenuComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'applicant-qr',
    component: ApplicantQrComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'apply',
    component: ApplyMembershipComponent
  },
  {
    path: 'verification',
    component: VerificationMenuComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'verification-qr',
    component: VerificationQrComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'verify-by',
    component: VerifyByComponent
  },
  {
    path: '',
    component: StartComponent
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
