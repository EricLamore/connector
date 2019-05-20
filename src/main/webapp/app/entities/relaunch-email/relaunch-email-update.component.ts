import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRelaunchEmail, RelaunchEmail } from 'app/shared/model/relaunch-email.model';
import { RelaunchEmailService } from './relaunch-email.service';

@Component({
  selector: 'jhi-relaunch-email-update',
  templateUrl: './relaunch-email-update.component.html'
})
export class RelaunchEmailUpdateComponent implements OnInit {
  relaunchEmail: IRelaunchEmail;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    relaunchSubject: [],
    relaunchBody: [],
    relaunchEndBody: [],
    relaunchSignatureLinkBody: []
  });

  constructor(protected relaunchEmailService: RelaunchEmailService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ relaunchEmail }) => {
      this.updateForm(relaunchEmail);
      this.relaunchEmail = relaunchEmail;
    });
  }

  updateForm(relaunchEmail: IRelaunchEmail) {
    this.editForm.patchValue({
      id: relaunchEmail.id,
      relaunchSubject: relaunchEmail.relaunchSubject,
      relaunchBody: relaunchEmail.relaunchBody,
      relaunchEndBody: relaunchEmail.relaunchEndBody,
      relaunchSignatureLinkBody: relaunchEmail.relaunchSignatureLinkBody
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const relaunchEmail = this.createFromForm();
    if (relaunchEmail.id !== undefined) {
      this.subscribeToSaveResponse(this.relaunchEmailService.update(relaunchEmail));
    } else {
      this.subscribeToSaveResponse(this.relaunchEmailService.create(relaunchEmail));
    }
  }

  private createFromForm(): IRelaunchEmail {
    const entity = {
      ...new RelaunchEmail(),
      id: this.editForm.get(['id']).value,
      relaunchSubject: this.editForm.get(['relaunchSubject']).value,
      relaunchBody: this.editForm.get(['relaunchBody']).value,
      relaunchEndBody: this.editForm.get(['relaunchEndBody']).value,
      relaunchSignatureLinkBody: this.editForm.get(['relaunchSignatureLinkBody']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelaunchEmail>>) {
    result.subscribe((res: HttpResponse<IRelaunchEmail>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
