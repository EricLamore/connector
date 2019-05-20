import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEmails, Emails } from 'app/shared/model/emails.model';
import { EmailsService } from './emails.service';
import { IStartEmail } from 'app/shared/model/start-email.model';
import { StartEmailService } from 'app/entities/start-email';
import { IEndEmail } from 'app/shared/model/end-email.model';
import { EndEmailService } from 'app/entities/end-email';
import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';
import { RelaunchEmailService } from 'app/entities/relaunch-email';

@Component({
  selector: 'jhi-emails-update',
  templateUrl: './emails-update.component.html'
})
export class EmailsUpdateComponent implements OnInit {
  emails: IEmails;
  isSaving: boolean;

  startemails: IStartEmail[];

  endemails: IEndEmail[];

  relaunchemails: IRelaunchEmail[];

  editForm = this.fb.group({
    id: [],
    logo: [],
    logoContentType: [],
    language: [],
    startEmail: [],
    endEmail: [],
    relaunchEmail: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected emailsService: EmailsService,
    protected startEmailService: StartEmailService,
    protected endEmailService: EndEmailService,
    protected relaunchEmailService: RelaunchEmailService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ emails }) => {
      this.updateForm(emails);
      this.emails = emails;
    });
    this.startEmailService
      .query({ filter: 'emails-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IStartEmail[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStartEmail[]>) => response.body)
      )
      .subscribe(
        (res: IStartEmail[]) => {
          if (!this.emails.startEmail || !this.emails.startEmail.id) {
            this.startemails = res;
          } else {
            this.startEmailService
              .find(this.emails.startEmail.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IStartEmail>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IStartEmail>) => subResponse.body)
              )
              .subscribe(
                (subRes: IStartEmail) => (this.startemails = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.endEmailService
      .query({ filter: 'emails-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEndEmail[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEndEmail[]>) => response.body)
      )
      .subscribe(
        (res: IEndEmail[]) => {
          if (!this.emails.endEmail || !this.emails.endEmail.id) {
            this.endemails = res;
          } else {
            this.endEmailService
              .find(this.emails.endEmail.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEndEmail>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEndEmail>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEndEmail) => (this.endemails = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.relaunchEmailService
      .query({ filter: 'emails-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRelaunchEmail[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRelaunchEmail[]>) => response.body)
      )
      .subscribe(
        (res: IRelaunchEmail[]) => {
          if (!this.emails.relaunchEmail || !this.emails.relaunchEmail.id) {
            this.relaunchemails = res;
          } else {
            this.relaunchEmailService
              .find(this.emails.relaunchEmail.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRelaunchEmail>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRelaunchEmail>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRelaunchEmail) => (this.relaunchemails = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(emails: IEmails) {
    this.editForm.patchValue({
      id: emails.id,
      logo: emails.logo,
      logoContentType: emails.logoContentType,
      language: emails.language,
      startEmail: emails.startEmail,
      endEmail: emails.endEmail,
      relaunchEmail: emails.relaunchEmail
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
    const emails = this.createFromForm();
    if (emails.id !== undefined) {
      this.subscribeToSaveResponse(this.emailsService.update(emails));
    } else {
      this.subscribeToSaveResponse(this.emailsService.create(emails));
    }
  }

  private createFromForm(): IEmails {
    const entity = {
      ...new Emails(),
      id: this.editForm.get(['id']).value,
      logoContentType: this.editForm.get(['logoContentType']).value,
      logo: this.editForm.get(['logo']).value,
      language: this.editForm.get(['language']).value,
      startEmail: this.editForm.get(['startEmail']).value,
      endEmail: this.editForm.get(['endEmail']).value,
      relaunchEmail: this.editForm.get(['relaunchEmail']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmails>>) {
    result.subscribe((res: HttpResponse<IEmails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackStartEmailById(index: number, item: IStartEmail) {
    return item.id;
  }

  trackEndEmailById(index: number, item: IEndEmail) {
    return item.id;
  }

  trackRelaunchEmailById(index: number, item: IRelaunchEmail) {
    return item.id;
  }
}
