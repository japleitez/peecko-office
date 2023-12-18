import { IArticleItem, NewArticleItem } from './article-item.model';

export const sampleWithRequiredData: IArticleItem = {
  id: 16052,
};

export const sampleWithPartialData: IArticleItem = {
  id: 4744,
  code: 'whose',
};

export const sampleWithFullData: IArticleItem = {
  id: 26758,
  previous: 'ah against',
  code: 'although charter bagel',
  next: 'mid sweetly ew',
};

export const sampleWithNewData: NewArticleItem = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
