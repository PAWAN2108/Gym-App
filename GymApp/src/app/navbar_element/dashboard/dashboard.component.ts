import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  constructor() {}

  logoutUser() {
    localStorage.clear();
    window.location.replace('http://localhost:4200');
  }

  ngOnInit(): void {
    let userToken = localStorage.getItem('gym-app-token');
    if (!localStorage.getItem('gym-token-verified')) {
      document.body.innerHTML = '';
      alert('Sorry you are Not Logged in 🤷‍♀️ to access this page.');
      window.location.replace('http://localhost:4200');
    }
    axios
      .post('http://localhost:8080/user/verifyToken', {
        userToken,
      })
      .then((res) => localStorage.setItem('gym-token-verified', res.data))
      .catch((w) => console.log(w));
  }
}
