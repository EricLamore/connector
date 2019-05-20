/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConnectorTestModule } from '../../../test.module';
import { StartEmailComponent } from 'app/entities/start-email/start-email.component';
import { StartEmailService } from 'app/entities/start-email/start-email.service';
import { StartEmail } from 'app/shared/model/start-email.model';

describe('Component Tests', () => {
  describe('StartEmail Management Component', () => {
    let comp: StartEmailComponent;
    let fixture: ComponentFixture<StartEmailComponent>;
    let service: StartEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [StartEmailComponent],
        providers: []
      })
        .overrideTemplate(StartEmailComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StartEmailComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StartEmailService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StartEmail('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.startEmails[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
