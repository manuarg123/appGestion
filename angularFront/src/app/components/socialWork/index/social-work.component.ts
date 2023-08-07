import { Component, NgModule } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SocialWorkFormComponent } from '../form/social-work-form.component';
@Component({
  selector: 'app-social-work',
  templateUrl: './social-work.component.html',
  styleUrls: ['./social-work.component.css']
})
export class SocialWorkComponent {
  constructor(private dialog: MatDialog) { }

  openSocialWorkForm(): void {
    this.dialog.open(SocialWorkFormComponent, {
      width:'700px'
    });
  }
}
