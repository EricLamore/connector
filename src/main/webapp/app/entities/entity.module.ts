import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'event',
        loadChildren: './event/event.module#ConnectorEventModule'
      },
      {
        path: 'sms',
        loadChildren: './sms/sms.module#ConnectorSMSModule'
      },
      {
        path: 'emails',
        loadChildren: './emails/emails.module#ConnectorEmailsModule'
      },
      {
        path: 'start-email',
        loadChildren: './start-email/start-email.module#ConnectorStartEmailModule'
      },
      {
        path: 'relaunch-email',
        loadChildren: './relaunch-email/relaunch-email.module#ConnectorRelaunchEmailModule'
      },
      {
        path: 'end-email',
        loadChildren: './end-email/end-email.module#ConnectorEndEmailModule'
      },
      {
        path: 'profile',
        loadChildren: './profile/profile.module#ConnectorProfileModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConnectorEntityModule {}
