import { Moment } from 'moment';

export const enum EventType {
  SYNCHRONIZATION = 'SYNCHRONIZATION',
  ADD_ACCOUNT = 'ADD_ACCOUNT',
  UPDATE_ACCOUNT = 'UPDATE_ACCOUNT',
  DELETE_ACCOUNT = 'DELETE_ACCOUNT',
  TASK_END = 'TASK_END',
  CREATE_CONNECTOR = 'CREATE_CONNECTOR',
  ERROR = 'ERROR'
}

export const enum ConnectorType {
  PPM = 'PPM',
  CRM = 'CRM',
  BILLING = 'BILLING',
  TICKETING = 'TICKETING',
  MRR_REPORT = 'MRR_REPORT',
  UNIVERSIGN = 'UNIVERSIGN'
}

export interface IEvent {
  id?: string;
  parentId?: string;
  masterEvent?: boolean;
  type?: EventType;
  from?: ConnectorType;
  to?: ConnectorType;
  exception?: string;
  message?: string;
  creationDate?: Moment;
  childSize?: number;
  parameter?: string;
}

export class Event implements IEvent {
  constructor(
    public id?: string,
    public parentId?: string,
    public masterEvent?: boolean,
    public type?: EventType,
    public from?: ConnectorType,
    public to?: ConnectorType,
    public exception?: string,
    public message?: string,
    public creationDate?: Moment,
    public childSize?: number,
    public parameter?: string
  ) {
    this.masterEvent = this.masterEvent || false;
  }
}
