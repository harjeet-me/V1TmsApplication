import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmsV1ApplicationSharedModule } from 'app/shared/shared.module';
import { PaymentComponent } from './payment.component';
import { PaymentDetailComponent } from './payment-detail.component';
import { PaymentUpdateComponent } from './payment-update.component';
import { PaymentDeleteDialogComponent } from './payment-delete-dialog.component';
import { paymentRoute } from './payment.route';

@NgModule({
  imports: [TmsV1ApplicationSharedModule, RouterModule.forChild(paymentRoute)],
  declarations: [PaymentComponent, PaymentDetailComponent, PaymentUpdateComponent, PaymentDeleteDialogComponent],
  entryComponents: [PaymentDeleteDialogComponent]
})
export class TmsV1ApplicationPaymentModule {}
