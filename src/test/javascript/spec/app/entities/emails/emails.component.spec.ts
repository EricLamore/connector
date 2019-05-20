/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConnectorTestModule } from '../../../test.module';
import { EmailsComponent } from 'app/entities/emails/emails.component';
import { EmailsService } from 'app/entities/emails/emails.service';
import { Emails } from 'app/shared/model/emails.model';

describe('Component Tests', () => {
  describe('Emails Management Component', () => {
    let comp: EmailsComponent;
    let fixture: ComponentFixture<EmailsComponent>;
    let service: EmailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EmailsComponent],
        providers: []
      })
        .overrideTemplate(EmailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Emails('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emails[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
