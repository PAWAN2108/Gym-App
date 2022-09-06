import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor() {}
  @ViewChild('emailInput')
  emailInput!: ElementRef;
  @ViewChild('passwordInput')
  passwordInput!: ElementRef;
  ngOnInit(): void {}

  loginUser(): void {
    let apiUrl = 'http://localhost:8080';
    let username = this.emailInput.nativeElement.value;
    let password = this.passwordInput.nativeElement.value;
    console.log(username + ' ' + password);

    // let userToken = localStorage.getItem('gym-app-token');
    axios
      .post('http://localhost:8080/user/loginUser', {
        username,
        password,
        // headers: {
        //   Authorization:
        //     'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmhpc2hla2dpZGRlIiwiZXhwIjoxNjUxMzU0Mjk5LCJpYXQiOjE2NTEzMTgyOTl9.QT3a8vvdqy3Ohtu-kq2uBJ1UTbBxtzjZNuM1sPhrQ0A',
        //   'Content-Type': 'application/json',
        // },
      })
      .then((res) => {
        if (res.status == 404) {
          console.log('No such user exists');
        }
        console.log(`Token Stored in local storage ` + res.data);
        localStorage.setItem('gym-app-token', res.data);
        redirectUser();
      })
      .catch((error) => {
        alert(`😕 Sorry either the password or the username is invalid!`);
      });
  }
}
function redirectUser() {
  axios.defaults.headers.common[
    'Authorization'
  ] = `Bearer ${localStorage.getItem('gym-app-token')}`;
  axios
    .post('http://localhost:8080/user/loginRedirect')
    .then((res) => {
      let userToken = localStorage.getItem('gym-app-token');
      axios
        .post('http://localhost:8080/user/verifyToken', {
          userToken,
        })
        .then((res) => localStorage.setItem('gym-token-verified', res.data))
        .catch((w) => console.log(w));
      if (localStorage.getItem('gym-token-verified'))
        window.location.replace(res.data);
    })
    .catch((e) => console.log(e));
}
