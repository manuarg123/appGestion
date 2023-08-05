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
import { ProvinceFormComponent } from './components/province/form/province-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from './components/common/confirmation-dialog/confirmation-dialog.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MedicalCenterFormComponent } from './components/medical-center/form/medical-center-form.component';
import { PhoneFormComponent } from './components/phone/phone-form/phone-form.component';
import { EmailFormComponent } from './components/email/email-form/email-form.component';
import { IdentificationFormComponent } from './components/identification/identification-form/identification-form.component';

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
    ProvinceFormComponent,
    ConfirmationDialogComponent,
    MedicalCenterFormComponent,
    PhoneFormComponent,
    EmailFormComponent,
    IdentificationFormComponent
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, BrowserAnimationsModule, MatDialogModule, MatSnackBarModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
