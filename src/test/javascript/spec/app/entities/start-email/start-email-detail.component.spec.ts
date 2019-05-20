/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { StartEmailDetailComponent } from 'app/entities/start-email/start-email-detail.component';
import { StartEmail } from 'app/shared/model/start-email.model';

describe('Component Tests', () => {
  describe('StartEmail Management Detail Component', () => {
    let comp: StartEmailDetailComponent;
    let fixture: ComponentFixture<StartEmailDetailComponent>;
    const route = ({ data: of({ startEmail: new StartEmail('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [StartEmailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StartEmailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StartEmailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.startEmail).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
