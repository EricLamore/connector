import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISMS, SMS } from 'app/shared/model/sms.model';
import { SMSService } from './sms.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

@Component({
  selector: 'jhi-sms-update',
  templateUrl: './sms-update.component.html'
})
export class SMSUpdateComponent implements OnInit {
  sMS: ISMS;
  isSaving: boolean;

  profiles: IProfile[];

  editForm = this.fb.group({
    id: [],
    smsAuthText: [],
    language: [],
    profile: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sMSService: SMSService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sMS }) => {
      this.updateForm(sMS);
      this.sMS = sMS;
    });
    this.profileService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProfile[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProfile[]>) => response.body)
      )
      .subscribe((res: IProfile[]) => (this.profiles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sMS: ISMS) {
    this.editForm.patchValue({
      id: sMS.id,
      smsAuthText: sMS.smsAuthText,
      language: sMS.language,
      profile: sMS.profile
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sMS = this.createFromForm();
    if (sMS.id !== undefined) {
      this.subscribeToSaveResponse(this.sMSService.update(sMS));
    } else {
      this.subscribeToSaveResponse(this.sMSService.create(sMS));
    }
  }

  private createFromForm(): ISMS {
    const entity = {
      ...new SMS(),
      id: this.editForm.get(['id']).value,
      smsAuthText: this.editForm.get(['smsAuthText']).value,
      language: this.editForm.get(['language']).value,
      profile: this.editForm.get(['profile']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISMS>>) {
    result.subscribe((res: HttpResponse<ISMS>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProfileById(index: number, item: IProfile) {
    return item.id;
  }
}
