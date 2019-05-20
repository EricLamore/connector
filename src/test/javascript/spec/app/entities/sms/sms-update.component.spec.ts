/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { SMSUpdateComponent } from 'app/entities/sms/sms-update.component';
import { SMSService } from 'app/entities/sms/sms.service';
import { SMS } from 'app/shared/model/sms.model';

describe('Component Tests', () => {
  describe('SMS Management Update Component', () => {
    let comp: SMSUpdateComponent;
    let fixture: ComponentFixture<SMSUpdateComponent>;
    let service: SMSService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [SMSUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SMSUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SMSUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SMSService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SMS('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SMS();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
