import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEndEmail, EndEmail } from 'app/shared/model/end-email.model';
import { EndEmailService } from './end-email.service';

@Component({
  selector: 'jhi-end-email-update',
  templateUrl: './end-email-update.component.html'
})
export class EndEmailUpdateComponent implements OnInit {
  endEmail: IEndEmail;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    endSubject: [],
    endBody: []
  });

  constructor(protected endEmailService: EndEmailService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ endEmail }) => {
      this.updateForm(endEmail);
      this.endEmail = endEmail;
    });
  }

  updateForm(endEmail: IEndEmail) {
    this.editForm.patchValue({
      id: endEmail.id,
      endSubject: endEmail.endSubject,
      endBody: endEmail.endBody
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const endEmail = this.createFromForm();
    if (endEmail.id !== undefined) {
      this.subscribeToSaveResponse(this.endEmailService.update(endEmail));
    } else {
      this.subscribeToSaveResponse(this.endEmailService.create(endEmail));
    }
  }

  private createFromForm(): IEndEmail {
    const entity = {
      ...new EndEmail(),
      id: this.editForm.get(['id']).value,
      endSubject: this.editForm.get(['endSubject']).value,
      endBody: this.editForm.get(['endBody']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndEmail>>) {
    result.subscribe((res: HttpResponse<IEndEmail>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
