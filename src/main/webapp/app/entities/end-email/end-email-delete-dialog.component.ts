import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndEmail } from 'app/shared/model/end-email.model';
import { EndEmailService } from './end-email.service';

@Component({
  selector: 'jhi-end-email-delete-dialog',
  templateUrl: './end-email-delete-dialog.component.html'
})
export class EndEmailDeleteDialogComponent {
  endEmail: IEndEmail;

  constructor(protected endEmailService: EndEmailService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.endEmailService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'endEmailListModification',
        content: 'Deleted an endEmail'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-end-email-delete-popup',
  template: ''
})
export class EndEmailDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ endEmail }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EndEmailDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.endEmail = endEmail;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/end-email', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/end-email', { outlets: { popup: null } }]);
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
