import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISMS } from 'app/shared/model/sms.model';
import { SMSService } from './sms.service';

@Component({
  selector: 'jhi-sms-delete-dialog',
  templateUrl: './sms-delete-dialog.component.html'
})
export class SMSDeleteDialogComponent {
  sMS: ISMS;

  constructor(protected sMSService: SMSService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.sMSService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sMSListModification',
        content: 'Deleted an sMS'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sms-delete-popup',
  template: ''
})
export class SMSDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sMS }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SMSDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sMS = sMS;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sms', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sms', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
