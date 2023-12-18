import dayjs from 'dayjs/esm';

import { IApsUser, NewApsUser } from './aps-user.model';

export const sampleWithRequiredData: IApsUser = {
  id: 26867,
  name: 'psst snoopy when',
  username: 'creepy rhythm',
  usernameVerified: 'N',
  privateEmail: 'upward',
  privateVerified: 'N',
  language: 'DE',
  active: 'N',
};

export const sampleWithPartialData: IApsUser = {
  id: 14736,
  name: 'jolly pointless step-son',
  username: 'along alongside excitement',
  usernameVerified: 'Y',
  privateEmail: 'limber phooey pish',
  privateVerified: 'Y',
  language: 'EN',
  active: 'N',
  password: 'wherever yahoo exorcise',
};

export const sampleWithFullData: IApsUser = {
  id: 498,
  name: 'lest clearly metallurgist',
  username: 'well-informed',
  usernameVerified: 'Y',
  privateEmail: 'overestimate service',
  privateVerified: 'N',
  language: 'FR',
  license: 'incidentally besides',
  active: 'Y',
  password: 'how incidentally packetize',
  created: dayjs('2023-12-17T21:25'),
  updated: dayjs('2023-12-17T19:32'),
};

export const sampleWithNewData: NewApsUser = {
  name: 'versus onto',
  username: 'lanky',
  usernameVerified: 'N',
  privateEmail: 'offensively hm',
  privateVerified: 'N',
  language: 'DE',
  active: 'Y',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
