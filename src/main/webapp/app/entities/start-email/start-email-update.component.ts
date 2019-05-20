import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IStartEmail, StartEmail } from 'app/shared/model/start-email.model';
import { StartEmailService } from './start-email.service';

@Component({
  selector: 'jhi-start-email-update',
  templateUrl: './start-email-update.component.html'
})
export class StartEmailUpdateComponent implements OnInit {
  startEmail: IStartEmail;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    startSubject: [],
    startBody: [],
    endBody: [],
    signatureLinkBody: []
  });

  constructor(protected startEmailService: StartEmailService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ startEmail }) => {
      this.updateForm(startEmail);
      this.startEmail = startEmail;
    });
  }

  updateForm(startEmail: IStartEmail) {
    this.editForm.patchValue({
      id: startEmail.id,
      startSubject: startEmail.startSubject,
      startBody: startEmail.startBody,
      endBody: startEmail.endBody,
      signatureLinkBody: startEmail.signatureLinkBody
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const startEmail = this.createFromForm();
    if (startEmail.id !== undefined) {
      this.subscribeToSaveResponse(this.startEmailService.update(startEmail));
    } else {
      this.subscribeToSaveResponse(this.startEmailService.create(startEmail));
    }
  }

  private createFromForm(): IStartEmail {
    const entity = {
      ...new StartEmail(),
      id: this.editForm.get(['id']).value,
      startSubject: this.editForm.get(['startSubject']).value,
      startBody: this.editForm.get(['startBody']).value,
      endBody: this.editForm.get(['endBody']).value,
      signatureLinkBody: this.editForm.get(['signatureLinkBody']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStartEmail>>) {
    result.subscribe((res: HttpResponse<IStartEmail>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
