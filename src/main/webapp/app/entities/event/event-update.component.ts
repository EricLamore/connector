import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  event: IEvent;
  isSaving: boolean;
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    parentId: [],
    masterEvent: [],
    type: [],
    from: [],
    to: [],
    exception: [],
    message: [],
    creationDate: [],
    childSize: [],
    parameter: []
  });

  constructor(protected eventService: EventService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ event }) => {
      this.updateForm(event);
      this.event = event;
    });
  }

  updateForm(event: IEvent) {
    this.editForm.patchValue({
      id: event.id,
      parentId: event.parentId,
      masterEvent: event.masterEvent,
      type: event.type,
      from: event.from,
      to: event.to,
      exception: event.exception,
      message: event.message,
      creationDate: event.creationDate,
      childSize: event.childSize,
      parameter: event.parameter
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    const entity = {
      ...new Event(),
      id: this.editForm.get(['id']).value,
      parentId: this.editForm.get(['parentId']).value,
      masterEvent: this.editForm.get(['masterEvent']).value,
      type: this.editForm.get(['type']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      exception: this.editForm.get(['exception']).value,
      message: this.editForm.get(['message']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      childSize: this.editForm.get(['childSize']).value,
      parameter: this.editForm.get(['parameter']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
    result.subscribe((res: HttpResponse<IEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
