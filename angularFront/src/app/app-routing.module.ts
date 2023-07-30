import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth.guard';
import { ContactosComponent } from './components/contactos/contactos.component';
import { MedicalCenterComponent } from './components/medical-center/index/medical-center.component';
import { ABMsComponent } from './components/abms/abms.component';
import { ProvinceComponent } from './components/province/index/province.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'contactos', component: ContactosComponent, canActivate: [AuthGuard] },
  { path: 'medicalCenter', component: MedicalCenterComponent, canActivate: [AuthGuard] },
  { path: 'ABMs', component: ABMsComponent, canActivate: [AuthGuard] },
  { path: 'province', component: ProvinceComponent, canActivate: [AuthGuard]},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
