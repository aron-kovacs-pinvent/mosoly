import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AdminMenuComponent } from './admin-menu/admin-menu.component';
import { VerificationMenuComponent } from './verification-menu/verification-menu.component';
import { ApplyMembershipComponent } from './apply-membership/apply-membership.component';
import { ApplicantQrComponent } from './applicant-qr/applicant-qr.component';
import { VerificationQrComponent } from './verification-qr/verification-qr.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatIconModule, MatInputModule, MatSelectModule, MatSnackBarModule} from '@angular/material';
import {StartComponent} from "./start/start.component";
import {AuthService} from "./_services/auth.service";
import {CommonModule} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {QRCodeModule} from "angular2-qrcode";
import { VerifyByComponent } from './verify-by/verify-by.component';
import {VerificationService} from "./_services/verification.service";
import {AuthGuard} from "./_guards/auth.guard";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminMenuComponent,
    VerificationMenuComponent,
    ApplyMembershipComponent,
    ApplicantQrComponent,
    VerificationQrComponent,
    StartComponent,
    VerifyByComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatButtonModule,
    QRCodeModule,
    MatIconModule,
    MatSnackBarModule,
    MatSelectModule
  ],
  providers: [
    AuthService,
    VerificationService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
