import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StartEmail } from 'app/shared/model/start-email.model';
import { StartEmailService } from './start-email.service';
import { StartEmailComponent } from './start-email.component';
import { StartEmailDetailComponent } from './start-email-detail.component';
import { StartEmailUpdateComponent } from './start-email-update.component';
import { StartEmailDeletePopupComponent } from './start-email-delete-dialog.component';
import { IStartEmail } from 'app/shared/model/start-email.model';

@Injectable({ providedIn: 'root' })
export class StartEmailResolve implements Resolve<IStartEmail> {
  constructor(private service: StartEmailService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStartEmail> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StartEmail>) => response.ok),
        map((startEmail: HttpResponse<StartEmail>) => startEmail.body)
      );
    }
    return of(new StartEmail());
  }
}

export const startEmailRoute: Routes = [
  {
    path: '',
    component: StartEmailComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.startEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StartEmailDetailComponent,
    resolve: {
      startEmail: StartEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.startEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StartEmailUpdateComponent,
    resolve: {
      startEmail: StartEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.startEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StartEmailUpdateComponent,
    resolve: {
      startEmail: StartEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.startEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const startEmailPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StartEmailDeletePopupComponent,
    resolve: {
      startEmail: StartEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.startEmail.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
