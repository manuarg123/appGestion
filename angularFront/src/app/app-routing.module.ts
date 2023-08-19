import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth.guard';
import { ContactosComponent } from './components/contactos/contactos.component';
import { MedicalCenterComponent } from './components/medical-center/index/medical-center.component';
import { ABMsComponent } from './components/abms/abms.component';
import { ProvinceComponent } from './components/province/index/province.component';
import { SocialWorkComponent } from './components/socialWork/index/social-work.component';
import { LocationComponent } from './components/location/index/location.component';
import { EmailTypeComponent } from './components/email-type/index/email-type.component';
import { PhoneTypeComponent } from './components/phone-type/index/phone-type.component';
import { IdentificationTypeComponent } from './components/identification-type/index/identification-type.component';
import { SpecialtyComponent } from './components/specialty/index/specialty.component';
import { GenderComponent } from './components/gender/index/gender.component';
import { PayoutStatusComponent } from './components/payout-status/index/payout-status.component';
import { PayoutConceptComponent } from './components/payout-concept/index/payout-concept.component';
import { PaymentTypeComponent } from './components/payment-type/index/payment-type.component';
import { ProfessionalComponent } from './components/professional/index/professional.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'contactos', component: ContactosComponent, canActivate: [AuthGuard] },
  { path: 'medicalCenter', component: MedicalCenterComponent, canActivate: [AuthGuard] },
  { path: 'professional', component: ProfessionalComponent, canActivate: [AuthGuard] },
  { path: 'socialWork', component: SocialWorkComponent, canActivate: [AuthGuard] },
  { path: 'ABMs', component: ABMsComponent, canActivate: [AuthGuard] },
  { path: 'province', component: ProvinceComponent, canActivate: [AuthGuard]},
  { path: 'location', component: LocationComponent, canActivate: [AuthGuard]},
  { path: 'emailType', component: EmailTypeComponent, canActivate: [AuthGuard]},
  { path: 'phoneType', component: PhoneTypeComponent, canActivate: [AuthGuard]},
  { path: 'identificationType', component: IdentificationTypeComponent, canActivate: [AuthGuard]},
  { path: 'specialty', component: SpecialtyComponent, canActivate: [AuthGuard]},
  { path: 'gender', component: GenderComponent, canActivate: [AuthGuard]},
  { path: 'payoutStatus', component: PayoutStatusComponent, canActivate: [AuthGuard]},
  { path: 'payoutConcept', component: PayoutConceptComponent, canActivate: [AuthGuard]},
  { path: 'paymentType', component: PaymentTypeComponent, canActivate: [AuthGuard]},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
