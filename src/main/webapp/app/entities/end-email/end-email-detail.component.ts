import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndEmail } from 'app/shared/model/end-email.model';

@Component({
  selector: 'jhi-end-email-detail',
  templateUrl: './end-email-detail.component.html'
})
export class EndEmailDetailComponent implements OnInit {
  endEmail: IEndEmail;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ endEmail }) => {
      this.endEmail = endEmail;
    });
  }

  previousState() {
    window.history.back();
  }
}
