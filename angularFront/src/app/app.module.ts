import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ContactosComponent } from './components/contactos/contactos.component';
import { MedicalCenterComponent } from './components/medical-center/index/medical-center.component';
import { MedicalCenterListComponent } from './components/medical-center/list/medical-center-list.component';
import { ABMsComponent } from './components/abms/abms.component';
import { ProvinceComponent } from './components/province/index/province.component';
import { ProvinceListComponent } from './components/province/list/province-list.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    NavbarComponent,
    LogoutComponent,
    ContactosComponent,
    MedicalCenterComponent,
    MedicalCenterListComponent,
    ABMsComponent,
    ProvinceComponent,
    ProvinceListComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
