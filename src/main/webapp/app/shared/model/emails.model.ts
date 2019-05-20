import { IStartEmail } from 'app/shared/model/start-email.model';
import { IEndEmail } from 'app/shared/model/end-email.model';
import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';

export const enum Language {
  FRENCH = 'FRENCH',
  ENGLISH = 'ENGLISH',
  SPANISH = 'SPANISH',
  ITALIAN = 'ITALIAN',
  GERMAN = 'GERMAN',
  PORTUGUESE = 'PORTUGUESE',
  DUTCH = 'DUTCH',
  ROMANIAN = 'ROMANIAN',
  POLISH = 'POLISH'
}

export interface IEmails {
  id?: string;
  logoContentType?: string;
  logo?: any;
  language?: Language;
  startEmail?: IStartEmail;
  endEmail?: IEndEmail;
  relaunchEmail?: IRelaunchEmail;
}

export class Emails implements IEmails {
  constructor(
    public id?: string,
    public logoContentType?: string,
    public logo?: any,
    public language?: Language,
    public startEmail?: IStartEmail,
    public endEmail?: IEndEmail,
    public relaunchEmail?: IRelaunchEmail
  ) {}
}
