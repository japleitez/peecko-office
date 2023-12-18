import dayjs from 'dayjs/esm';

import { ICoach, NewCoach } from './coach.model';

export const sampleWithRequiredData: ICoach = {
  id: 8385,
  type: 'FITNESS',
  name: 'knowledgeably spin leg',
};

export const sampleWithPartialData: ICoach = {
  id: 5532,
  type: 'WELLNESS',
  name: 'face',
  email: 'Gretchen_OKon89@gmail.com',
  website: 'yippee',
  instagram: 'mankind',
  speaks: 'oof snorer neatly',
  resume: 'besides',
  created: dayjs('2023-12-18T02:28'),
  updated: dayjs('2023-12-18T09:05'),
};

export const sampleWithFullData: ICoach = {
  id: 22374,
  type: 'WELLNESS',
  name: 'the outside whereas',
  email: 'Sylvan.Williamson69@hotmail.com',
  website: 'as harald',
  instagram: 'considering thinking',
  phoneNumber: 'offensively',
  country: 'Maldives',
  speaks: 'for designate spook',
  resume: 'intensely',
  notes: 'close but',
  created: dayjs('2023-12-18T04:51'),
  updated: dayjs('2023-12-18T01:27'),
};

export const sampleWithNewData: NewCoach = {
  type: 'WELLNESS',
  name: 'off slowly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
