/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConnectorTestModule } from '../../../test.module';
import { StartEmailDeleteDialogComponent } from 'app/entities/start-email/start-email-delete-dialog.component';
import { StartEmailService } from 'app/entities/start-email/start-email.service';

describe('Component Tests', () => {
  describe('StartEmail Management Delete Component', () => {
    let comp: StartEmailDeleteDialogComponent;
    let fixture: ComponentFixture<StartEmailDeleteDialogComponent>;
    let service: StartEmailService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [StartEmailDeleteDialogComponent]
      })
        .overrideTemplate(StartEmailDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StartEmailDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StartEmailService);
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
