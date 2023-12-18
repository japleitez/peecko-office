import { IArticle, NewArticle } from './article.model';

export const sampleWithRequiredData: IArticle = {
  id: 4786,
  code: 'loll cross aha',
  title: 'reconfirm uh-huh',
  language: 'DE',
};

export const sampleWithPartialData: IArticle = {
  id: 16583,
  code: 'intermix briskly kind',
  title: 'rubbish randomisation than',
  subtitle: 'gosh gosh',
  duration: 11321,
  language: 'FR',
  thumbnail: 'chief',
};

export const sampleWithFullData: IArticle = {
  id: 9960,
  code: 'kindheartedly',
  title: 'aggressive reheat after',
  subtitle: 'evocation after querulous',
  summary: 'qualified wetly',
  duration: 21644,
  language: 'DE',
  thumbnail: 'hubcap till till',
  audioUrl: 'into',
  content: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewArticle = {
  code: 'repurpose',
  title: 'bud reveal sharply',
  language: 'ES',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
