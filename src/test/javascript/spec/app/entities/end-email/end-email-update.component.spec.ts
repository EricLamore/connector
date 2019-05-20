/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { EndEmailUpdateComponent } from 'app/entities/end-email/end-email-update.component';
import { EndEmailService } from 'app/entities/end-email/end-email.service';
import { EndEmail } from 'app/shared/model/end-email.model';

describe('Component Tests', () => {
  describe('EndEmail Management Update Component', () => {
    let comp: EndEmailUpdateComponent;
    let fixture: ComponentFixture<EndEmailUpdateComponent>;
    let service: EndEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EndEmailUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EndEmailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EndEmailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EndEmailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EndEmail('123');
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
        const entity = new EndEmail();
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
