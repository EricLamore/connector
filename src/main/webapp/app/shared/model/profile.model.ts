import { IEmails } from 'app/shared/model/emails.model';
import { ISMS } from 'app/shared/model/sms.model';

export const enum Environment {
  SANDBOX = 'SANDBOX',
  PRODUCTION = 'PRODUCTION'
}

export interface IProfile {
  id?: string;
  name?: string;
  environment?: Environment;
  lifeTransaction?: number;
  callbackUrl?: string;
  redirectionUrl?: string;
  googleAnalyticsId?: string;
  logoContentType?: string;
  logo?: any;
  displayedName?: string;
  clickUrl?: string;
  smsOtpLifetime?: number;
  logoAlign?: string;
  colorIntro?: string;
  sizeTextintro?: string;
  colorBody?: string;
  sizeTextBody?: string;
  colorButon?: string;
  sizeTextButton?: string;
  colorTextButton?: string;
  textAlign?: string;
  storage?: boolean;
  storageService?: string;
  multiStamp?: boolean;
  merge?: boolean;
  emails?: IEmails;
  sms?: ISMS[];
}

export class Profile implements IProfile {
  constructor(
    public id?: string,
    public name?: string,
    public environment?: Environment,
    public lifeTransaction?: number,
    public callbackUrl?: string,
    public redirectionUrl?: string,
    public googleAnalyticsId?: string,
    public logoContentType?: string,
    public logo?: any,
    public displayedName?: string,
    public clickUrl?: string,
    public smsOtpLifetime?: number,
    public logoAlign?: string,
    public colorIntro?: string,
    public sizeTextintro?: string,
    public colorBody?: string,
    public sizeTextBody?: string,
    public colorButon?: string,
    public sizeTextButton?: string,
    public colorTextButton?: string,
    public textAlign?: string,
    public storage?: boolean,
    public storageService?: string,
    public multiStamp?: boolean,
    public merge?: boolean,
    public emails?: IEmails,
    public sms?: ISMS[]
  ) {
    this.storage = this.storage || false;
    this.multiStamp = this.multiStamp || false;
    this.merge = this.merge || false;
  }
}
