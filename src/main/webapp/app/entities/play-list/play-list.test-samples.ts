import dayjs from 'dayjs/esm';

import { IPlayList, NewPlayList } from './play-list.model';

export const sampleWithRequiredData: IPlayList = {
  id: 10445,
  name: 'unique skive',
  counter: 27764,
  created: dayjs('2023-12-17T12:32'),
  updated: dayjs('2023-12-17T22:03'),
};

export const sampleWithPartialData: IPlayList = {
  id: 18664,
  name: 'seclude weekender definitive',
  counter: 8,
  created: dayjs('2023-12-18T01:43'),
  updated: dayjs('2023-12-17T19:17'),
};

export const sampleWithFullData: IPlayList = {
  id: 423,
  name: 'monthly',
  counter: 28274,
  created: dayjs('2023-12-18T11:09'),
  updated: dayjs('2023-12-18T01:54'),
};

export const sampleWithNewData: NewPlayList = {
  name: 'enfeeble toothpaste quaver',
  counter: 11152,
  created: dayjs('2023-12-18T10:29'),
  updated: dayjs('2023-12-18T01:44'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
