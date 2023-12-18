import dayjs from 'dayjs/esm';

import { IArticleCategory, NewArticleCategory } from './article-category.model';

export const sampleWithRequiredData: IArticleCategory = {
  id: 14103,
  code: 'and affirm chunter',
  title: 'lanky disgust if',
  label: 'briskly',
};

export const sampleWithPartialData: IArticleCategory = {
  id: 23035,
  code: 'vanquish chard',
  title: 'webinar wetly',
  label: 'hmph so',
  release: dayjs('2023-12-17T23:09'),
  archived: dayjs('2023-12-18T03:50'),
};

export const sampleWithFullData: IArticleCategory = {
  id: 31937,
  code: 'anniversary',
  title: 'crossly wafer',
  label: 'an um',
  created: dayjs('2023-12-17T17:56'),
  release: dayjs('2023-12-17T21:24'),
  archived: dayjs('2023-12-18T08:37'),
};

export const sampleWithNewData: NewArticleCategory = {
  code: 'norm but oof',
  title: 'woot onto wisely',
  label: 'combat rigidly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
