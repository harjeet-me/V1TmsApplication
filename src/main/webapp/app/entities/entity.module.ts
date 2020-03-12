import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company-profile',
        loadChildren: () => import('./company-profile/company-profile.module').then(m => m.TmsV1ApplicationCompanyProfileModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.TmsV1ApplicationCustomerModule)
      },
      {
        path: 'trip',
        loadChildren: () => import('./trip/trip.module').then(m => m.TmsV1ApplicationTripModule)
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.TmsV1ApplicationInvoiceModule)
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./invoice-item/invoice-item.module').then(m => m.TmsV1ApplicationInvoiceItemModule)
      },
      {
        path: 'product-item',
        loadChildren: () => import('./product-item/product-item.module').then(m => m.TmsV1ApplicationProductItemModule)
      },
      {
        path: 'accounts',
        loadChildren: () => import('./accounts/accounts.module').then(m => m.TmsV1ApplicationAccountsModule)
      },
      {
        path: 'transactions-record',
        loadChildren: () => import('./transactions-record/transactions-record.module').then(m => m.TmsV1ApplicationTransactionsRecordModule)
      },
      {
        path: 'container',
        loadChildren: () => import('./container/container.module').then(m => m.TmsV1ApplicationContainerModule)
      },
      {
        path: 'equipment',
        loadChildren: () => import('./equipment/equipment.module').then(m => m.TmsV1ApplicationEquipmentModule)
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.TmsV1ApplicationInsuranceModule)
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.TmsV1ApplicationContactModule)
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.TmsV1ApplicationDriverModule)
      },
      {
        path: 'carrier',
        loadChildren: () => import('./carrier/carrier.module').then(m => m.TmsV1ApplicationCarrierModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.TmsV1ApplicationLocationModule)
      },
      {
        path: 'email',
        loadChildren: () => import('./email/email.module').then(m => m.TmsV1ApplicationEmailModule)
      },
      {
        path: 'invoice-history',
        loadChildren: () => import('./invoice-history/invoice-history.module').then(m => m.TmsV1ApplicationInvoiceHistoryModule)
      },
      {
        path: 'account-history',
        loadChildren: () => import('./account-history/account-history.module').then(m => m.TmsV1ApplicationAccountHistoryModule)
      },
      {
        path: 'report',
        loadChildren: () => import('./report/report.module').then(m => m.TmsV1ApplicationReportModule)
      },
      {
        path: 'file-system',
        loadChildren: () => import('./file-system/file-system.module').then(m => m.TmsV1ApplicationFileSystemModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TmsV1ApplicationEntityModule {}
