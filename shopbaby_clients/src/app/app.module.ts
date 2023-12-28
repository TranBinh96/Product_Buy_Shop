import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { OrderconfirmComponent } from './orderconfirm/orderconfirm.component';
import { OrderComponent } from './order/order.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DetailProductComponent } from './detail-product/detail-product.component';

@NgModule({
  declarations: [
    HomeComponent,
    RegisterComponent,
    OrderconfirmComponent,
    OrderComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    DetailProductComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [RegisterComponent]
})
export class AppModule { }
