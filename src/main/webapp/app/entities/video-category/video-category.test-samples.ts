import dayjs from 'dayjs/esm';

import { IVideoCategory, NewVideoCategory } from './video-category.model';

export const sampleWithRequiredData: IVideoCategory = {
  id: 13994,
  code: 'evoke loosely suddenly',
  title: 'year',
  label: 'laughter',
};

export const sampleWithPartialData: IVideoCategory = {
  id: 31902,
  code: 'rejuvenate worth',
  title: 'while heir hilarious',
  label: 'spray',
  created: dayjs('2023-12-17T21:29'),
  archived: dayjs('2023-12-17T17:34'),
};

export const sampleWithFullData: IVideoCategory = {
  id: 17507,
  code: 'boohoo',
  title: 'nor brr',
  label: 'scimitar where pfft',
  created: dayjs('2023-12-17T14:01'),
  released: dayjs('2023-12-18T00:24'),
  archived: dayjs('2023-12-17T20:47'),
};

export const sampleWithNewData: NewVideoCategory = {
  code: 'needily',
  title: 'greedy',
  label: 'rapidly scarily utterly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
