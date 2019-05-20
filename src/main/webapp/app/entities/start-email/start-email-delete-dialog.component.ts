import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStartEmail } from 'app/shared/model/start-email.model';
import { StartEmailService } from './start-email.service';

@Component({
  selector: 'jhi-start-email-delete-dialog',
  templateUrl: './start-email-delete-dialog.component.html'
})
export class StartEmailDeleteDialogComponent {
  startEmail: IStartEmail;

  constructor(
    protected startEmailService: StartEmailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.startEmailService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'startEmailListModification',
        content: 'Deleted an startEmail'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-start-email-delete-popup',
  template: ''
})
export class StartEmailDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ startEmail }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StartEmailDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.startEmail = startEmail;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/start-email', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/start-email', { outlets: { popup: null } }]);
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
