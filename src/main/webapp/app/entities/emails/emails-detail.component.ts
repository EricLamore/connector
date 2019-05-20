import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmails } from 'app/shared/model/emails.model';

@Component({
  selector: 'jhi-emails-detail',
  templateUrl: './emails-detail.component.html'
})
export class EmailsDetailComponent implements OnInit {
  emails: IEmails;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emails }) => {
      this.emails = emails;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
