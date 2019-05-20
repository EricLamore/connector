/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IProfile, Profile, Environment } from 'app/shared/model/profile.model';

describe('Service Tests', () => {
  describe('Profile Service', () => {
    let injector: TestBed;
    let service: ProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfile;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProfileService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Profile(
        'ID',
        'AAAAAAA',
        Environment.SANDBOX,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Profile', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Profile(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Profile', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            environment: 'BBBBBB',
            lifeTransaction: 1,
            callbackUrl: 'BBBBBB',
            redirectionUrl: 'BBBBBB',
            googleAnalyticsId: 'BBBBBB',
            logo: 'BBBBBB',
            displayedName: 'BBBBBB',
            clickUrl: 'BBBBBB',
            smsOtpLifetime: 1,
            logoAlign: 'BBBBBB',
            colorIntro: 'BBBBBB',
            sizeTextintro: 'BBBBBB',
            colorBody: 'BBBBBB',
            sizeTextBody: 'BBBBBB',
            colorButon: 'BBBBBB',
            sizeTextButton: 'BBBBBB',
            colorTextButton: 'BBBBBB',
            textAlign: 'BBBBBB',
            storage: true,
            storageService: 'BBBBBB',
            multiStamp: true,
            merge: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Profile', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            environment: 'BBBBBB',
            lifeTransaction: 1,
            callbackUrl: 'BBBBBB',
            redirectionUrl: 'BBBBBB',
            googleAnalyticsId: 'BBBBBB',
            logo: 'BBBBBB',
            displayedName: 'BBBBBB',
            clickUrl: 'BBBBBB',
            smsOtpLifetime: 1,
            logoAlign: 'BBBBBB',
            colorIntro: 'BBBBBB',
            sizeTextintro: 'BBBBBB',
            colorBody: 'BBBBBB',
            sizeTextBody: 'BBBBBB',
            colorButon: 'BBBBBB',
            sizeTextButton: 'BBBBBB',
            colorTextButton: 'BBBBBB',
            textAlign: 'BBBBBB',
            storage: true,
            storageService: 'BBBBBB',
            multiStamp: true,
            merge: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Profile', async () => {
        const rxPromise = service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
