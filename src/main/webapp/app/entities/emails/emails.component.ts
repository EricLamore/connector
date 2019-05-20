import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IEmails } from 'app/shared/model/emails.model';
import { AccountService } from 'app/core';
import { EmailsService } from './emails.service';

@Component({
  selector: 'jhi-emails',
  templateUrl: './emails.component.html'
})
export class EmailsComponent implements OnInit, OnDestroy {
  emails: IEmails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected emailsService: EmailsService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.emailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IEmails[]>) => res.ok),
        map((res: HttpResponse<IEmails[]>) => res.body)
      )
      .subscribe(
        (res: IEmails[]) => {
          this.emails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmails) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInEmails() {
    this.eventSubscriber = this.eventManager.subscribe('emailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
