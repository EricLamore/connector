/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { RelaunchEmailDetailComponent } from 'app/entities/relaunch-email/relaunch-email-detail.component';
import { RelaunchEmail } from 'app/shared/model/relaunch-email.model';

describe('Component Tests', () => {
  describe('RelaunchEmail Management Detail Component', () => {
    let comp: RelaunchEmailDetailComponent;
    let fixture: ComponentFixture<RelaunchEmailDetailComponent>;
    const route = ({ data: of({ relaunchEmail: new RelaunchEmail('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [RelaunchEmailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RelaunchEmailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelaunchEmailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.relaunchEmail).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
