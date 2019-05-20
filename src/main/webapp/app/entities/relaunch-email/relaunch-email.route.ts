import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RelaunchEmail } from 'app/shared/model/relaunch-email.model';
import { RelaunchEmailService } from './relaunch-email.service';
import { RelaunchEmailComponent } from './relaunch-email.component';
import { RelaunchEmailDetailComponent } from './relaunch-email-detail.component';
import { RelaunchEmailUpdateComponent } from './relaunch-email-update.component';
import { RelaunchEmailDeletePopupComponent } from './relaunch-email-delete-dialog.component';
import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';

@Injectable({ providedIn: 'root' })
export class RelaunchEmailResolve implements Resolve<IRelaunchEmail> {
  constructor(private service: RelaunchEmailService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRelaunchEmail> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RelaunchEmail>) => response.ok),
        map((relaunchEmail: HttpResponse<RelaunchEmail>) => relaunchEmail.body)
      );
    }
    return of(new RelaunchEmail());
  }
}

export const relaunchEmailRoute: Routes = [
  {
    path: '',
    component: RelaunchEmailComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.relaunchEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RelaunchEmailDetailComponent,
    resolve: {
      relaunchEmail: RelaunchEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.relaunchEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RelaunchEmailUpdateComponent,
    resolve: {
      relaunchEmail: RelaunchEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.relaunchEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RelaunchEmailUpdateComponent,
    resolve: {
      relaunchEmail: RelaunchEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.relaunchEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const relaunchEmailPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RelaunchEmailDeletePopupComponent,
    resolve: {
      relaunchEmail: RelaunchEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.relaunchEmail.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
