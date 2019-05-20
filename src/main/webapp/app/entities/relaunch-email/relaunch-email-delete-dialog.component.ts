import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';
import { RelaunchEmailService } from './relaunch-email.service';

@Component({
  selector: 'jhi-relaunch-email-delete-dialog',
  templateUrl: './relaunch-email-delete-dialog.component.html'
})
export class RelaunchEmailDeleteDialogComponent {
  relaunchEmail: IRelaunchEmail;

  constructor(
    protected relaunchEmailService: RelaunchEmailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.relaunchEmailService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'relaunchEmailListModification',
        content: 'Deleted an relaunchEmail'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-relaunch-email-delete-popup',
  template: ''
})
export class RelaunchEmailDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ relaunchEmail }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RelaunchEmailDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.relaunchEmail = relaunchEmail;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/relaunch-email', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/relaunch-email', { outlets: { popup: null } }]);
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
