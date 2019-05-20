/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { EmailsUpdateComponent } from 'app/entities/emails/emails-update.component';
import { EmailsService } from 'app/entities/emails/emails.service';
import { Emails } from 'app/shared/model/emails.model';

describe('Component Tests', () => {
  describe('Emails Management Update Component', () => {
    let comp: EmailsUpdateComponent;
    let fixture: ComponentFixture<EmailsUpdateComponent>;
    let service: EmailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EmailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Emails('123');
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
        const entity = new Emails();
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
