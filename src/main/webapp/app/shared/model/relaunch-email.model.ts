export interface IRelaunchEmail {
  id?: string;
  relaunchSubject?: string;
  relaunchBody?: string;
  relaunchEndBody?: string;
  relaunchSignatureLinkBody?: string;
}

export class RelaunchEmail implements IRelaunchEmail {
  constructor(
    public id?: string,
    public relaunchSubject?: string,
    public relaunchBody?: string,
    public relaunchEndBody?: string,
    public relaunchSignatureLinkBody?: string
  ) {}
}
