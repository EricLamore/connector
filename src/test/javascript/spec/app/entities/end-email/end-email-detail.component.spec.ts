/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { EndEmailDetailComponent } from 'app/entities/end-email/end-email-detail.component';
import { EndEmail } from 'app/shared/model/end-email.model';

describe('Component Tests', () => {
  describe('EndEmail Management Detail Component', () => {
    let comp: EndEmailDetailComponent;
    let fixture: ComponentFixture<EndEmailDetailComponent>;
    const route = ({ data: of({ endEmail: new EndEmail('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EndEmailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EndEmailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EndEmailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.endEmail).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
