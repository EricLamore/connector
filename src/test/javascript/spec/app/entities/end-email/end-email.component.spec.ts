/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConnectorTestModule } from '../../../test.module';
import { EndEmailComponent } from 'app/entities/end-email/end-email.component';
import { EndEmailService } from 'app/entities/end-email/end-email.service';
import { EndEmail } from 'app/shared/model/end-email.model';

describe('Component Tests', () => {
  describe('EndEmail Management Component', () => {
    let comp: EndEmailComponent;
    let fixture: ComponentFixture<EndEmailComponent>;
    let service: EndEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EndEmailComponent],
        providers: []
      })
        .overrideTemplate(EndEmailComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EndEmailComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EndEmailService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EndEmail('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.endEmails[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
