import dayjs from 'dayjs/esm';

import { ICustomer, NewCustomer } from './customer.model';

export const sampleWithRequiredData: ICustomer = {
  id: 5425,
  code: 'zowie',
  name: 'frantically vice',
  country: 'Faroe Islands',
  state: 'ACTIVE',
};

export const sampleWithPartialData: ICustomer = {
  id: 582,
  code: 'um',
  name: 'worn toward gobble',
  country: 'Virgin Islands, British',
  state: 'TRIAL',
  closeReason: 'why wherever unsteady',
  bank: 'truthfully ha',
  logo: 'however',
  notes: 'white',
  closed: dayjs('2023-12-17T19:38'),
};

export const sampleWithFullData: ICustomer = {
  id: 31956,
  code: 'defensive aw',
  name: 'usable mockingly old-fashioned',
  country: 'Qatar',
  membership: 'remedy daily unripe',
  state: 'ACTIVE',
  closeReason: 'or dance',
  emailDomains: 'poison so round',
  vatId: 'researcher',
  bank: 'how intermix ah',
  iban: 'PK52KUWZ6006857025304091',
  logo: 'bah after rescue',
  notes: 'since',
  created: dayjs('2023-12-17T16:01'),
  updated: dayjs('2023-12-18T06:00'),
  trialed: dayjs('2023-12-18T01:43'),
  declined: dayjs('2023-12-17T21:47'),
  activated: dayjs('2023-12-18T02:49'),
  closed: dayjs('2023-12-18T07:43'),
};

export const sampleWithNewData: NewCustomer = {
  code: 'yum apropos peak',
  name: 'an bristle broiler',
  country: 'New Caledonia',
  state: 'TRIAL',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
