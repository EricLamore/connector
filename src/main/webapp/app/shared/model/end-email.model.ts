export interface IEndEmail {
  id?: string;
  endSubject?: string;
  endBody?: string;
}

export class EndEmail implements IEndEmail {
  constructor(public id?: string, public endSubject?: string, public endBody?: string) {}
}
