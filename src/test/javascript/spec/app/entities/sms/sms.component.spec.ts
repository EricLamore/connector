/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ConnectorTestModule } from '../../../test.module';
import { SMSComponent } from 'app/entities/sms/sms.component';
import { SMSService } from 'app/entities/sms/sms.service';
import { SMS } from 'app/shared/model/sms.model';

describe('Component Tests', () => {
  describe('SMS Management Component', () => {
    let comp: SMSComponent;
    let fixture: ComponentFixture<SMSComponent>;
    let service: SMSService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [SMSComponent],
        providers: []
      })
        .overrideTemplate(SMSComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SMSComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SMSService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SMS('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sMS[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
