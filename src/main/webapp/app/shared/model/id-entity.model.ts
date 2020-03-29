export interface IIdEntity {
  id?: number;
}

export class IdEntity implements IIdEntity {
  constructor(public id?: number) {}
}
