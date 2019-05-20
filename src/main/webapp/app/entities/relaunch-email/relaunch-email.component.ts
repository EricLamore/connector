import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';
import { AccountService } from 'app/core';
import { RelaunchEmailService } from './relaunch-email.service';

@Component({
  selector: 'jhi-relaunch-email',
  templateUrl: './relaunch-email.component.html'
})
export class RelaunchEmailComponent implements OnInit, OnDestroy {
  relaunchEmails: IRelaunchEmail[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected relaunchEmailService: RelaunchEmailService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.relaunchEmailService
      .query()
      .pipe(
        filter((res: HttpResponse<IRelaunchEmail[]>) => res.ok),
        map((res: HttpResponse<IRelaunchEmail[]>) => res.body)
      )
      .subscribe(
        (res: IRelaunchEmail[]) => {
          this.relaunchEmails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRelaunchEmails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRelaunchEmail) {
    return item.id;
  }

  registerChangeInRelaunchEmails() {
    this.eventSubscriber = this.eventManager.subscribe('relaunchEmailListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
