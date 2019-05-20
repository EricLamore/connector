import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SMS } from 'app/shared/model/sms.model';
import { SMSService } from './sms.service';
import { SMSComponent } from './sms.component';
import { SMSDetailComponent } from './sms-detail.component';
import { SMSUpdateComponent } from './sms-update.component';
import { SMSDeletePopupComponent } from './sms-delete-dialog.component';
import { ISMS } from 'app/shared/model/sms.model';

@Injectable({ providedIn: 'root' })
export class SMSResolve implements Resolve<ISMS> {
  constructor(private service: SMSService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISMS> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SMS>) => response.ok),
        map((sMS: HttpResponse<SMS>) => sMS.body)
      );
    }
    return of(new SMS());
  }
}

export const sMSRoute: Routes = [
  {
    path: '',
    component: SMSComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.sMS.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SMSDetailComponent,
    resolve: {
      sMS: SMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.sMS.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SMSUpdateComponent,
    resolve: {
      sMS: SMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.sMS.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SMSUpdateComponent,
    resolve: {
      sMS: SMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.sMS.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sMSPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SMSDeletePopupComponent,
    resolve: {
      sMS: SMSResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.sMS.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
