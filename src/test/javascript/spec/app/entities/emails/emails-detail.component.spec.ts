/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConnectorTestModule } from '../../../test.module';
import { EmailsDetailComponent } from 'app/entities/emails/emails-detail.component';
import { Emails } from 'app/shared/model/emails.model';

describe('Component Tests', () => {
  describe('Emails Management Detail Component', () => {
    let comp: EmailsDetailComponent;
    let fixture: ComponentFixture<EmailsDetailComponent>;
    const route = ({ data: of({ emails: new Emails('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ConnectorTestModule],
        declarations: [EmailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emails).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
