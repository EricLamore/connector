/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConnectorTestModule } from '../../../test.module';
import { RelaunchEmailDeleteDialogComponent } from 'app/entities/relaunch-email/relaunch-email-delete-dialog.component';
import { RelaunchEmailService } from 'app/entities/relaunch-email/relaunch-email.service';

describe('Component Tests', () => {
  describe('RelaunchEmail Management Delete Component', () => {
    let comp: RelaunchEmailDeleteDialogComponent;
    let fixture: ComponentFixture<RelaunchEmailDeleteDialogComponent>;
    let service: RelaunchEmailService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [RelaunchEmailDeleteDialogComponent]
      })
        .overrideTemplate(RelaunchEmailDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelaunchEmailDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelaunchEmailService);
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
