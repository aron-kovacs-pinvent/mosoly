import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable()
export class AuthService {

  constructor(
    private http: HttpClient
  ) {

  }

  public set(data: any){
    localStorage.setItem("user", JSON.stringify(data));
  }

  public check(attr: string) {
    return this.http.get('/api/users/check?attr=' + attr, {
      headers: {
        Authorization: "Bearer tokentoken"
      }
    })
  }

  public me() {
    return this.http.get('/api/users/me', {
      headers: {
        Authorization: "Bearer " + this.user().jwt
      }
    })
  }

  public register(payload: any) {
    return this.http.post('/api/register', payload, {
      headers: {
        Authorization: "Bearer tokentoken"
      }
    });
  }

  public login(attr: string, login: string, password: string) {
    const payload = {
      attr: attr,
      principal: login,
      credential: password
    };
    return this.http.post('/api/login', payload, {
      headers: {
        Authorization: "Bearer tokentoken"
      }
    });
  }

  public user() {
    return JSON.parse(localStorage.getItem("user"));
  }

  public isLoggedIn() {
    return localStorage.getItem("user") !== null;
  }


}
