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
import { PhoneTypeComponent } from './components/phone-type/index/phone-type.component';
import { PhoneTypeFormComponent } from './components/phone-type/form/phone-type-form.component';
import { PhoneTypeListComponent } from './components/phone-type/list/phone-type-list.component';
import { IdentificationTypeComponent } from './components/identification-type/index/identification-type.component';
import { IdentificationTypeFormComponent } from './components/identification-type/form/identification-type-form.component';
import { IdentificationTypeListComponent } from './components/identification-type/list/identification-type-list.component';
import { SpecialtyComponent } from './components/specialty/index/specialty.component';
import { SpecialtyFormComponent } from './components/specialty/form/specialty-form.component';
import { SpecialtyListComponent } from './components/specialty/list/specialty-list.component';
import { GenderComponent } from './components/gender/index/gender.component';
import { GenderFormComponent } from './components/gender/form/gender-form.component';
import { GenderListComponent } from './components/gender/list/gender-list.component';
import { PayoutStatusComponent } from './components/payout-status/index/payout-status.component';
import { PayoutStatusFormComponent } from './components/payout-status/form/payout-status-form.component';
import { PayoutStatusListComponent } from './components/payout-status/list/payout-status-list.component';
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
    EmailTypeListComponent,
    PhoneTypeComponent,
    PhoneTypeFormComponent,
    PhoneTypeListComponent,
    IdentificationTypeComponent,
    IdentificationTypeFormComponent,
    IdentificationTypeListComponent,
    SpecialtyComponent,
    SpecialtyFormComponent,
    SpecialtyListComponent,
    GenderComponent,
    GenderFormComponent,
    GenderListComponent,
    PayoutStatusComponent,
    PayoutStatusFormComponent,
    PayoutStatusListComponent
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, BrowserAnimationsModule, MatDialogModule, MatSnackBarModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
