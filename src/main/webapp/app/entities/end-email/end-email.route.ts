import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EndEmail } from 'app/shared/model/end-email.model';
import { EndEmailService } from './end-email.service';
import { EndEmailComponent } from './end-email.component';
import { EndEmailDetailComponent } from './end-email-detail.component';
import { EndEmailUpdateComponent } from './end-email-update.component';
import { EndEmailDeletePopupComponent } from './end-email-delete-dialog.component';
import { IEndEmail } from 'app/shared/model/end-email.model';

@Injectable({ providedIn: 'root' })
export class EndEmailResolve implements Resolve<IEndEmail> {
  constructor(private service: EndEmailService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEndEmail> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EndEmail>) => response.ok),
        map((endEmail: HttpResponse<EndEmail>) => endEmail.body)
      );
    }
    return of(new EndEmail());
  }
}

export const endEmailRoute: Routes = [
  {
    path: '',
    component: EndEmailComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.endEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EndEmailDetailComponent,
    resolve: {
      endEmail: EndEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.endEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EndEmailUpdateComponent,
    resolve: {
      endEmail: EndEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.endEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EndEmailUpdateComponent,
    resolve: {
      endEmail: EndEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.endEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const endEmailPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EndEmailDeletePopupComponent,
    resolve: {
      endEmail: EndEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'connectorApp.endEmail.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
