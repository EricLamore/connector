import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ConnectorSharedModule } from 'app/shared';
import {
  SMSComponent,
  SMSDetailComponent,
  SMSUpdateComponent,
  SMSDeletePopupComponent,
  SMSDeleteDialogComponent,
  sMSRoute,
  sMSPopupRoute
} from './';

const ENTITY_STATES = [...sMSRoute, ...sMSPopupRoute];

@NgModule({
  imports: [ConnectorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SMSComponent, SMSDetailComponent, SMSUpdateComponent, SMSDeleteDialogComponent, SMSDeletePopupComponent],
  entryComponents: [SMSComponent, SMSUpdateComponent, SMSDeleteDialogComponent, SMSDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConnectorSMSModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
