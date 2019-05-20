import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEndEmail } from 'app/shared/model/end-email.model';
import { AccountService } from 'app/core';
import { EndEmailService } from './end-email.service';

@Component({
  selector: 'jhi-end-email',
  templateUrl: './end-email.component.html'
})
export class EndEmailComponent implements OnInit, OnDestroy {
  endEmails: IEndEmail[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected endEmailService: EndEmailService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.endEmailService
      .query()
      .pipe(
        filter((res: HttpResponse<IEndEmail[]>) => res.ok),
        map((res: HttpResponse<IEndEmail[]>) => res.body)
      )
      .subscribe(
        (res: IEndEmail[]) => {
          this.endEmails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEndEmails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEndEmail) {
    return item.id;
  }

  registerChangeInEndEmails() {
    this.eventSubscriber = this.eventManager.subscribe('endEmailListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
