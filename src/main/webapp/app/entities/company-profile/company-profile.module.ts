import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { CompanyProfileComponent } from './company-profile.component';
import { CompanyProfileDetailComponent } from './company-profile-detail.component';
import { CompanyProfileUpdateComponent } from './company-profile-update.component';
import { CompanyProfileDeleteDialogComponent } from './company-profile-delete-dialog.component';
import { companyProfileRoute } from './company-profile.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(companyProfileRoute)],
  declarations: [
    CompanyProfileComponent,
    CompanyProfileDetailComponent,
    CompanyProfileUpdateComponent,
    CompanyProfileDeleteDialogComponent
  ],
  entryComponents: [CompanyProfileDeleteDialogComponent]
})
export class TmsV1ApplicationCompanyProfileModule {}
