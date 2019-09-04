import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import has = Reflect.has;

@Injectable()
export class VerificationService {

  constructor(
    private http: HttpClient
  ) {

  }

  public getMentorName(hash: string) {
    return this.http.get('/api/mentors?hash=' + hash, {
      headers: {
        Authorization: "Bearer ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKemRXSWlPaUpCVUZCVlUwVlNJbjAudG54Zk0xTG5xOE9KTGU3STVLVHFCa0luTzBPQ1FyM0xfbGh4VlIwcmR4bw=="
      }
    })
  }

  public addMentor(hash: string) {
    const payload = {
      hash: hash
    };
    return this.http.post('/api/add-mentor', payload, {
      headers: {
        Authorization: "Bearer " + this.user().jwt
      }
    });
  }

  public nickName(name: string, account: string) {
    const payload = {
      value: name,
      account: account
    };
    return this.http.post('/api/users/nickname', payload, {
      headers: {
        Authorization: "Bearer " + this.user().jwt
      }
    });
  }

  private user() {
    return JSON.parse(localStorage.getItem("user"));
  }

  public validate(account: string) {
    const payload = {
      account: account
    };
    return this.http.post('/api/users/validate', payload, {
      headers: {
        Authorization: "Bearer " + this.user().jwt
      }
    });

  }

}
