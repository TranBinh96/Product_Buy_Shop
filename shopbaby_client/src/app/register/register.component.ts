import {Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {elementAt} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phone:string;
  password:string;
  retypePassword:string;
  fullname :string;
  address:string;
  isAccepted: boolean;
  dateOfBirth : Date;

  constructor() {
    this.phone='';
    this.password='';
    this.fullname='';
    this.retypePassword='';
    this.address='';
    this.isAccepted=false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear()-18);
  }
  register(){
   const message = 'phone : '+this.phone
                +'\npassword :'+this.password
                +'\nRe Type Password :'+this.retypePassword
                +'\nfullname :'+this.fullname
                +'\naddress :'+this.address
                +'\ndate :'+this.dateOfBirth
                +'\nisAccepted :'+this.isAccepted;

   console.log(message);
  }
  checkPasswordMatch(){
    if (this.password !== this.retypePassword)
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMismatch': true});
    else
      this.registerForm.form.controls['retypePassword'].setErrors(null);
  }

  checkAge(){
   
    const today = new Date();
    const  birthDate  = new Date(this.dateOfBirth);
    let age  = today.getFullYear() - birthDate.getFullYear();
    const  monthDiff = today.getMonth() - birthDate.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())){
      age--;
    }
    if (age < 18){
      this.registerForm.form.controls['dateOfBirth'].setErrors({'invalidAge' : true});
    }
    else
      this.registerForm.form.controls['dateOfBirth'].setErrors(null);
  }


}
