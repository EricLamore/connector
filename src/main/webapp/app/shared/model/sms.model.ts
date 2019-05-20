import { IProfile } from 'app/shared/model/profile.model';

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

export interface ISMS {
  id?: string;
  smsAuthText?: string;
  language?: Language;
  profile?: IProfile;
}

export class SMS implements ISMS {
  constructor(public id?: string, public smsAuthText?: string, public language?: Language, public profile?: IProfile) {}
}
