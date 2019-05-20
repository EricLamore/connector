import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ConnectorSharedModule } from 'app/shared';
import {
  EmailsComponent,
  EmailsDetailComponent,
  EmailsUpdateComponent,
  EmailsDeletePopupComponent,
  EmailsDeleteDialogComponent,
  emailsRoute,
  emailsPopupRoute
} from './';

const ENTITY_STATES = [...emailsRoute, ...emailsPopupRoute];

@NgModule({
  imports: [ConnectorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EmailsComponent, EmailsDetailComponent, EmailsUpdateComponent, EmailsDeleteDialogComponent, EmailsDeletePopupComponent],
  entryComponents: [EmailsComponent, EmailsUpdateComponent, EmailsDeleteDialogComponent, EmailsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConnectorEmailsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
