import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Emails } from 'app/shared/model/emails.model';
import { EmailsService } from './emails.service';
import { EmailsComponent } from './emails.component';
import { EmailsDetailComponent } from './emails-detail.component';
import { EmailsUpdateComponent } from './emails-update.component';
import { EmailsDeletePopupComponent } from './emails-delete-dialog.component';
import { IEmails } from 'app/shared/model/emails.model';

@Injectable({ providedIn: 'root' })
export class EmailsResolve implements Resolve<IEmails> {
  constructor(private service: EmailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmails> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Emails>) => response.ok),
        map((emails: HttpResponse<Emails>) => emails.body)
      );
    }
    return of(new Emails());
  }
}

export const emailsRoute: Routes = [
  {
    path: '',
    component: EmailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.emails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmailsDetailComponent,
    resolve: {
      emails: EmailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.emails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmailsUpdateComponent,
    resolve: {
      emails: EmailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.emails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmailsUpdateComponent,
    resolve: {
      emails: EmailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.emails.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmailsDeletePopupComponent,
    resolve: {
      emails: EmailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.emails.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
