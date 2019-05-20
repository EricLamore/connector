import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISMS } from 'app/shared/model/sms.model';

@Component({
  selector: 'jhi-sms-detail',
  templateUrl: './sms-detail.component.html'
})
export class SMSDetailComponent implements OnInit {
  sMS: ISMS;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sMS }) => {
      this.sMS = sMS;
    });
  }

  previousState() {
    window.history.back();
  }
}
