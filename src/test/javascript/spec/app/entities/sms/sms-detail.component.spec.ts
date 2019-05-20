/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { SMSDetailComponent } from 'app/entities/sms/sms-detail.component';
import { SMS } from 'app/shared/model/sms.model';

describe('Component Tests', () => {
  describe('SMS Management Detail Component', () => {
    let comp: SMSDetailComponent;
    let fixture: ComponentFixture<SMSDetailComponent>;
    const route = ({ data: of({ sMS: new SMS('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [SMSDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SMSDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SMSDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sMS).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
