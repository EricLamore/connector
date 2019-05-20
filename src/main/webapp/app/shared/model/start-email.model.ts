export interface IStartEmail {
  id?: string;
  startSubject?: string;
  startBody?: string;
  endBody?: string;
  signatureLinkBody?: string;
}

export class StartEmail implements IStartEmail {
  constructor(
    public id?: string,
    public startSubject?: string,
    public startBody?: string,
    public endBody?: string,
    public signatureLinkBody?: string
  ) {}
}
