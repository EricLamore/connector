/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { RelaunchEmailUpdateComponent } from 'app/entities/relaunch-email/relaunch-email-update.component';
import { RelaunchEmailService } from 'app/entities/relaunch-email/relaunch-email.service';
import { RelaunchEmail } from 'app/shared/model/relaunch-email.model';

describe('Component Tests', () => {
  describe('RelaunchEmail Management Update Component', () => {
    let comp: RelaunchEmailUpdateComponent;
    let fixture: ComponentFixture<RelaunchEmailUpdateComponent>;
    let service: RelaunchEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [RelaunchEmailUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RelaunchEmailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelaunchEmailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelaunchEmailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RelaunchEmail('123');
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
        const entity = new RelaunchEmail();
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
