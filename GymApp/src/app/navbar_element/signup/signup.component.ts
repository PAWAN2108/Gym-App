import {
  Component,
  ElementRef,
  NgModule,
  OnInit,
  ViewChild,
} from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-signup , lottie-animation-view-demo-app',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  signupUser = async () => {
    let fullName = this.fullName.nativeElement.value,
      password = this.passwordInput.nativeElement.value,
      confirmPassword = this.confirmPasswordInput.nativeElement.value,
      mobileNumber = this.mobileNumber.nativeElement.value,
      email = this.emailInput.nativeElement.value;
    let username = fullName.replace(' ', '');
    username = username.toLowerCase();
    let userDetails = {
      username,
      email,
      password,
      phone: mobileNumber,
    };
    console.log(JSON.stringify(userDetails));

    axios
      .post('http://localhost:8080/user/createUser', {
        ...userDetails,
        Headers: { 'content-type': 'application/json' },
      })
      .then((res) => {
        console.log(res.data);
        alert(
          `Hi ${fullName} Signup Succesful, your username is ${username} 🥳`
        );
        window.location.href = 'http://localhost:4200/';
      })
      .catch((e) => {
        if ((e.message = 'Network Error')) {
          console.log('Server not responding. Check internet. 😗');
        }
        console.log(e);
      });
    let userToken = localStorage.getItem('gym-app-token');
    if (!localStorage.getItem('gym-token-verified')) {
      window.location.replace('http://localhost:4200');
    }
  };
  constructor() {}

  ngOnInit(): void {}

  @ViewChild('fullName')
  fullName!: ElementRef;
  @ViewChild('emailInput')
  emailInput!: ElementRef;
  @ViewChild('mobileNumber')
  mobileNumber!: ElementRef;
  @ViewChild('passwordInput')
  passwordInput!: ElementRef;
  @ViewChild('confirmPasswordInput')
  confirmPasswordInput!: ElementRef;
}
