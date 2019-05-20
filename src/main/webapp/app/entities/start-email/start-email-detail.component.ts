import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStartEmail } from 'app/shared/model/start-email.model';

@Component({
  selector: 'jhi-start-email-detail',
  templateUrl: './start-email-detail.component.html'
})
export class StartEmailDetailComponent implements OnInit {
  startEmail: IStartEmail;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ startEmail }) => {
      this.startEmail = startEmail;
    });
  }

  previousState() {
    window.history.back();
  }
}
