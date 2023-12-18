import dayjs from 'dayjs/esm';

import { IArticleList, NewArticleList } from './article-list.model';

export const sampleWithRequiredData: IArticleList = {
  id: 19900,
  name: 'despite grounded',
  counter: 9510,
  created: dayjs('2023-12-18T00:39'),
  updated: dayjs('2023-12-17T22:52'),
};

export const sampleWithPartialData: IArticleList = {
  id: 7467,
  name: 'impure',
  counter: 1417,
  created: dayjs('2023-12-18T10:00'),
  updated: dayjs('2023-12-18T06:24'),
};

export const sampleWithFullData: IArticleList = {
  id: 18906,
  name: 'telephone intensely down',
  counter: 32019,
  created: dayjs('2023-12-18T10:12'),
  updated: dayjs('2023-12-18T06:44'),
};

export const sampleWithNewData: NewArticleList = {
  name: 'macaw accidentally out',
  counter: 1204,
  created: dayjs('2023-12-18T08:48'),
  updated: dayjs('2023-12-18T02:10'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
