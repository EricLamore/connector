/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ConnectorTestModule } from '../../../test.module';
import { SMSDeleteDialogComponent } from 'app/entities/sms/sms-delete-dialog.component';
import { SMSService } from 'app/entities/sms/sms.service';

describe('Component Tests', () => {
  describe('SMS Management Delete Component', () => {
    let comp: SMSDeleteDialogComponent;
    let fixture: ComponentFixture<SMSDeleteDialogComponent>;
    let service: SMSService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [SMSDeleteDialogComponent]
      })
        .overrideTemplate(SMSDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SMSDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SMSService);
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
