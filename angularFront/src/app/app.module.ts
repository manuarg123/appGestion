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
import { PersonFormComponent } from './components/person/form/person-form.component';
import { SocialWorkFormComponent } from './components/socialWork/form/social-work-form.component';
import { SocialWorkComponent } from './components/socialWork/index/social-work.component';
import { SocialWorkListComponent } from './components/socialWork/list/social-work-list.component';
import { LocationComponent } from './components/location/index/location.component';
import { LocationFormComponent } from './components/location/form/location-form.component';
import { LocationListComponent } from './components/location/list/location-list.component';
import { EmailTypeComponent } from './components/email-type/index/email-type.component';
import { EmailTypeFormComponent } from './components/email-type/form/email-type-form.component';
import { EmailTypeListComponent } from './components/email-type/list/email-type-list.component';
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
    IdentificationFormComponent,
    PersonFormComponent,
    SocialWorkFormComponent,
    SocialWorkComponent,
    SocialWorkListComponent,
    LocationComponent,
    LocationFormComponent,
    LocationListComponent,
    EmailTypeComponent,
    EmailTypeFormComponent,
    EmailTypeListComponent
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, BrowserAnimationsModule, MatDialogModule, MatSnackBarModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
