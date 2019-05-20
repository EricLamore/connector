import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmails } from 'app/shared/model/emails.model';
import { EmailsService } from './emails.service';

@Component({
  selector: 'jhi-emails-delete-dialog',
  templateUrl: './emails-delete-dialog.component.html'
})
export class EmailsDeleteDialogComponent {
  emails: IEmails;

  constructor(protected emailsService: EmailsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.emailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'emailsListModification',
        content: 'Deleted an emails'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-emails-delete-popup',
  template: ''
})
export class EmailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmailsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.emails = emails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/emails', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/emails', { outlets: { popup: null } }]);
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
