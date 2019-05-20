import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ConnectorSharedModule } from 'app/shared';
import {
  RelaunchEmailComponent,
  RelaunchEmailDetailComponent,
  RelaunchEmailUpdateComponent,
  RelaunchEmailDeletePopupComponent,
  RelaunchEmailDeleteDialogComponent,
  relaunchEmailRoute,
  relaunchEmailPopupRoute
} from './';

const ENTITY_STATES = [...relaunchEmailRoute, ...relaunchEmailPopupRoute];

@NgModule({
  imports: [ConnectorSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RelaunchEmailComponent,
    RelaunchEmailDetailComponent,
    RelaunchEmailUpdateComponent,
    RelaunchEmailDeleteDialogComponent,
    RelaunchEmailDeletePopupComponent
  ],
  entryComponents: [
    RelaunchEmailComponent,
    RelaunchEmailUpdateComponent,
    RelaunchEmailDeleteDialogComponent,
    RelaunchEmailDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConnectorRelaunchEmailModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
