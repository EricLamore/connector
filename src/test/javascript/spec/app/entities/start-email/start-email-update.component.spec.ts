/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { StartEmailUpdateComponent } from 'app/entities/start-email/start-email-update.component';
import { StartEmailService } from 'app/entities/start-email/start-email.service';
import { StartEmail } from 'app/shared/model/start-email.model';

describe('Component Tests', () => {
  describe('StartEmail Management Update Component', () => {
    let comp: StartEmailUpdateComponent;
    let fixture: ComponentFixture<StartEmailUpdateComponent>;
    let service: StartEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [StartEmailUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StartEmailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StartEmailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StartEmailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StartEmail('123');
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
        const entity = new StartEmail();
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
