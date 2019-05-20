/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConnectorTestModule } from '../../../test.module';
import { EndEmailDeleteDialogComponent } from 'app/entities/end-email/end-email-delete-dialog.component';
import { EndEmailService } from 'app/entities/end-email/end-email.service';

describe('Component Tests', () => {
  describe('EndEmail Management Delete Component', () => {
    let comp: EndEmailDeleteDialogComponent;
    let fixture: ComponentFixture<EndEmailDeleteDialogComponent>;
    let service: EndEmailService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EndEmailDeleteDialogComponent]
      })
        .overrideTemplate(EndEmailDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EndEmailDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EndEmailService);
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
