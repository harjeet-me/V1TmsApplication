import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { EmailComponent } from './email.component';
import { EmailDetailComponent } from './email-detail.component';
import { EmailUpdateComponent } from './email-update.component';
import { EmailDeleteDialogComponent } from './email-delete-dialog.component';
import { emailRoute } from './email.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(emailRoute)],
  declarations: [EmailComponent, EmailDetailComponent, EmailUpdateComponent, EmailDeleteDialogComponent],
  entryComponents: [EmailDeleteDialogComponent]
})
export class TmsV1ApplicationEmailModule {}
