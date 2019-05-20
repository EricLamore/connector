import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISMS } from 'app/shared/model/sms.model';
import { AccountService } from 'app/core';
import { SMSService } from './sms.service';

@Component({
  selector: 'jhi-sms',
  templateUrl: './sms.component.html'
})
export class SMSComponent implements OnInit, OnDestroy {
  sMS: ISMS[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sMSService: SMSService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sMSService
      .query()
      .pipe(
        filter((res: HttpResponse<ISMS[]>) => res.ok),
        map((res: HttpResponse<ISMS[]>) => res.body)
      )
      .subscribe(
        (res: ISMS[]) => {
          this.sMS = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSMS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISMS) {
    return item.id;
  }

  registerChangeInSMS() {
    this.eventSubscriber = this.eventManager.subscribe('sMSListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
