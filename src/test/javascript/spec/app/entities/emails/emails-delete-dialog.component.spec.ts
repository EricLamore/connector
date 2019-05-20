/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConnectorTestModule } from '../../../test.module';
import { EmailsDeleteDialogComponent } from 'app/entities/emails/emails-delete-dialog.component';
import { EmailsService } from 'app/entities/emails/emails.service';

describe('Component Tests', () => {
  describe('Emails Management Delete Component', () => {
    let comp: EmailsDeleteDialogComponent;
    let fixture: ComponentFixture<EmailsDeleteDialogComponent>;
    let service: EmailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EmailsDeleteDialogComponent]
      })
        .overrideTemplate(EmailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
