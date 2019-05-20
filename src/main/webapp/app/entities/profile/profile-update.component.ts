import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { IEmails } from 'app/shared/model/emails.model';
import { EmailsService } from 'app/entities/emails';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  profile: IProfile;
  isSaving: boolean;

  emails: IEmails[];

  editForm = this.fb.group({
    id: [],
    name: [],
    environment: [],
    lifeTransaction: [],
    callbackUrl: [],
    redirectionUrl: [],
    googleAnalyticsId: [],
    logo: [],
    logoContentType: [],
    displayedName: [],
    clickUrl: [],
    smsOtpLifetime: [],
    logoAlign: [],
    colorIntro: [],
    sizeTextintro: [],
    colorBody: [],
    sizeTextBody: [],
    colorButon: [],
    sizeTextButton: [],
    colorTextButton: [],
    textAlign: [],
    storage: [],
    storageService: [],
    multiStamp: [],
    merge: [],
    emails: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected profileService: ProfileService,
    protected emailsService: EmailsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);
      this.profile = profile;
    });
    this.emailsService
      .query({ filter: 'profile-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEmails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmails[]>) => response.body)
      )
      .subscribe(
        (res: IEmails[]) => {
          if (!this.profile.emails || !this.profile.emails.id) {
            this.emails = res;
          } else {
            this.emailsService
              .find(this.profile.emails.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEmails>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEmails>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEmails) => (this.emails = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(profile: IProfile) {
    this.editForm.patchValue({
      id: profile.id,
      name: profile.name,
      environment: profile.environment,
      lifeTransaction: profile.lifeTransaction,
      callbackUrl: profile.callbackUrl,
      redirectionUrl: profile.redirectionUrl,
      googleAnalyticsId: profile.googleAnalyticsId,
      logo: profile.logo,
      logoContentType: profile.logoContentType,
      displayedName: profile.displayedName,
      clickUrl: profile.clickUrl,
      smsOtpLifetime: profile.smsOtpLifetime,
      logoAlign: profile.logoAlign,
      colorIntro: profile.colorIntro,
      sizeTextintro: profile.sizeTextintro,
      colorBody: profile.colorBody,
      sizeTextBody: profile.sizeTextBody,
      colorButon: profile.colorButon,
      sizeTextButton: profile.sizeTextButton,
      colorTextButton: profile.colorTextButton,
      textAlign: profile.textAlign,
      storage: profile.storage,
      storageService: profile.storageService,
      multiStamp: profile.multiStamp,
      merge: profile.merge,
      emails: profile.emails
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    const entity = {
      ...new Profile(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      environment: this.editForm.get(['environment']).value,
      lifeTransaction: this.editForm.get(['lifeTransaction']).value,
      callbackUrl: this.editForm.get(['callbackUrl']).value,
      redirectionUrl: this.editForm.get(['redirectionUrl']).value,
      googleAnalyticsId: this.editForm.get(['googleAnalyticsId']).value,
      logoContentType: this.editForm.get(['logoContentType']).value,
      logo: this.editForm.get(['logo']).value,
      displayedName: this.editForm.get(['displayedName']).value,
      clickUrl: this.editForm.get(['clickUrl']).value,
      smsOtpLifetime: this.editForm.get(['smsOtpLifetime']).value,
      logoAlign: this.editForm.get(['logoAlign']).value,
      colorIntro: this.editForm.get(['colorIntro']).value,
      sizeTextintro: this.editForm.get(['sizeTextintro']).value,
      colorBody: this.editForm.get(['colorBody']).value,
      sizeTextBody: this.editForm.get(['sizeTextBody']).value,
      colorButon: this.editForm.get(['colorButon']).value,
      sizeTextButton: this.editForm.get(['sizeTextButton']).value,
      colorTextButton: this.editForm.get(['colorTextButton']).value,
      textAlign: this.editForm.get(['textAlign']).value,
      storage: this.editForm.get(['storage']).value,
      storageService: this.editForm.get(['storageService']).value,
      multiStamp: this.editForm.get(['multiStamp']).value,
      merge: this.editForm.get(['merge']).value,
      emails: this.editForm.get(['emails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>) {
    result.subscribe((res: HttpResponse<IProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEmailsById(index: number, item: IEmails) {
    return item.id;
  }
}
