import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStartEmail } from 'app/shared/model/start-email.model';
import { AccountService } from 'app/core';
import { StartEmailService } from './start-email.service';

@Component({
  selector: 'jhi-start-email',
  templateUrl: './start-email.component.html'
})
export class StartEmailComponent implements OnInit, OnDestroy {
  startEmails: IStartEmail[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected startEmailService: StartEmailService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.startEmailService
      .query()
      .pipe(
        filter((res: HttpResponse<IStartEmail[]>) => res.ok),
        map((res: HttpResponse<IStartEmail[]>) => res.body)
      )
      .subscribe(
        (res: IStartEmail[]) => {
          this.startEmails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStartEmails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStartEmail) {
    return item.id;
  }

  registerChangeInStartEmails() {
    this.eventSubscriber = this.eventManager.subscribe('startEmailListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
