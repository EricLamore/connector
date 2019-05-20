import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';

@Component({
  selector: 'jhi-relaunch-email-detail',
  templateUrl: './relaunch-email-detail.component.html'
})
export class RelaunchEmailDetailComponent implements OnInit {
  relaunchEmail: IRelaunchEmail;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ relaunchEmail }) => {
      this.relaunchEmail = relaunchEmail;
    });
  }

  previousState() {
    window.history.back();
  }
}
