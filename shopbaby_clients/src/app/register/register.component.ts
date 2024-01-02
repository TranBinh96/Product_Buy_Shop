import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import{HttpClient,HttpHeaders} from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm! : NgForm;
  phone:string;
  password:string;
  retypePassword:string;
  fullname:string;
  address:string;
  dateOfBirth:Date;
  isAccepted:boolean;

  constructor(private http: HttpClient, private router: Router){
    this.phone='11223344';
    this.password='1122';
    this.retypePassword ='1122';
    this.address='Vĩnh Bảo - Hải Phòng',
    this.fullname='Trần Thanh Bình';
    this.isAccepted=false;
    this.dateOfBirth=new Date();    
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear()-18)
  }

  onPhoneChange(){
    // console.log(`Phone type : ${this.phone}`);
  }
  register(){
    // const message =`name : ${this.phone}
    // \npass : ${this.password} 
    // \nretypePass : ${this.retypePassword} 
    // \nFullname  :  ${this.fullname}
    // \naddress : ${this.address}
    // \nisAccepted : ${this.isAccepted}
    // \nDate Of Birth : ${this.dateOfBirth}`
    // console.log(message);
    
    const apiUrl = "localhost:8084/api/v1/users/register";   
    const resigterData = {    
        "fullname": this.fullname,
        "phone_number": this.phone,
        "address": this.address,
        "password": this.password,
        "retype_password": this.retypePassword,
        "date_of_birthday": this.dateOfBirth,
        "facebook_account_id": 0,
        "google_account_id": 0,
        "role_id": 2
      }
      const headers = new HttpHeaders({
        'Content-Type': 'application/json'
      });

      this.http.post(apiUrl, resigterData, {headers}, )
      .subscribe({
          next: (response : any)=>{
            debugger
            this.router.navigate(['/login']);
          },
          complete:()=>{
            debugger
          },
          error : (error:any)=>{
            debugger
            alert('register user not success :' );
          }
        }
      );
  }

  checkPasswordMatch(){
    if(this.password !== this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch':true});
    }else{
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }
  checkAge(){
    if(this.dateOfBirth){
      const today = new Date();
      const birthDate  = new Date(this.dateOfBirth);
      let age  =  today.getFullYear() - birthDate.getFullYear();
      const monthDiff  = today.getMonth() - birthDate.getMonth();
      if(monthDiff <0 || (monthDiff===0 && today.getDate() < birthDate.getDate())){
        age --;
      }
      if(age < 18)
        this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidAge' : true});
      else
        this.registerForm.form.controls['dateOfBirth'].setErrors(null);
    }
  }

}
